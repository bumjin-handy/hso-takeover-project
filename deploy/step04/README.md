# 1. 목표
도커허브에 올라간 업무인수인계 도커이미지를 쿠버네티스에 올린다.

# 2. 다룰 내용
 - 디플로이와 서비스를 하나의 yml파일로 생성한다.
 - Ingress Nginx로 URL prefix로 따른 라우팅처리를 한다.
 
 참고:
 https://velog.io/@yange/Ingress%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-nginx-ingress-controller
k8s의 Ingress는 Layer 7에서의 요청을 처리할 수 있다.
외부로부터 들어오는 요청에 대한 Load Balancing, TLS/SSL 인증서 처리, 특정 HTTP 경로의 라우팅 등을 Ingress를 통해 자세하게 정의할 수 있다.

# 3. 실행환경 
- 도커 데스크탑에 쿠버네티스 실행가능한 상태에서 실습한다.
- Ingress Controller 설치되어 있어야 한다.
호드리 블로그(https://moon1z10.github.io/development/k8s-docker/) 참고 

https://www.youtube.com/watch?v=13y1tWEK2ZY
https://github.com/AnaisUrlichs/ingress-example

```powershell
PS C:\git\hso-takeover-project\deploy\step04> kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   112m
```

```powershell
PS C:\git\hso-takeover-project\deploy\step04> kubectl get svc -n ingress-nginx
NAME                                 TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)                      AGE
ingress-nginx-controller             LoadBalancer   10.103.1.89    localhost     80:30498/TCP,443:30767/TCP   4d2h
ingress-nginx-controller-admission   ClusterIP      10.97.49.122   <none>        443/TCP                      4d2h
```

# 4. 도커 이미지 deploy
이미지명: bumjin/hso-takeover-project:0.0.1-SNAPSHOT (https://hub.docker.com/repository/docker/bumjin/hso-takeover-project/general)


yaml에서 depolyment와 service관련 설정을 --- 로 구분하여 multiple 로 구성한다.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hso-takeover
  labels:
    app: hso-takeover
spec:
  selector:
    matchLabels:
      app: hso-takeover
  template:
    metadata:
      labels:
        app: hso-takeover
    spec:
      containers:
      - name: hso-takeover
        image: bumjin/hso-takeover-project:0.0.1-SNAPSHOT
---
apiVersion: v1
kind: Service
metadata:
  name: hso-takeover-svc
spec:
  type: ClusterIP
  selector:
    app: hso-takeover
  ports:
  - name: http
    port: 8080
```

```powershell
PS C:\git\hso-takeover-project\deploy\step04> kubectl apply -f deployment.yml
deployment.apps/hso-takeover created
service/hso-takeover-svc created
```

```powershell
PS C:\git\hso-takeover-project\deploy\step04> kubectl get all
NAME                                READY   STATUS    RESTARTS   AGE
pod/hso-takeover-7f7c7545b7-nw6vh   1/1     Running   0          16s

NAME                       TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/hso-takeover-svc   ClusterIP   10.104.160.27   <none>        8080/TCP   16s
service/kubernetes         ClusterIP   10.96.0.1       <none>        443/TCP    114m

NAME                           READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/hso-takeover   1/1     1            1           16s

NAME                                      DESIRED   CURRENT   READY   AGE
replicaset.apps/hso-takeover-7f7c7545b7   1         1         1       16s
```


이 서비스는 현재 ClusterIP로 실행되기에 외부에선 접속할 수 없다.

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /$1
    spec/ingress.class: nginx
  name: simple-example
spec:
  defaultBackend:
    service:
      name: hso-takeover-svc
      port:
        number: 8080
  ingressClassName: nginx
  rules:
  - host: bumjin.local
    http:
      paths:
      - backend:
          service:
            name: hso-takeover-svc
            port:
              number: 8080
        path: /one
        pathType: Prefix
```
bumjin.local 도메인으로 접속하면 defaultBackend 서비스인 hso-takeover-svc로 라우팅된다.
url에 /one path로 접속해도 hso-takeover-svc로 라우팅된다.

http://bumjin.local/
혹은
http://bumjin.local/one 로 접속


# Deployment 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step04> kubectl delete -f deployment.yml
deployment.apps "hso-takeover" deleted
service "hso-takeover-svc" deleted
```

# Ingress 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step05> kubectl delete -f ingress.yaml       
ingress.networking.k8s.io "simple-example" deleted
```