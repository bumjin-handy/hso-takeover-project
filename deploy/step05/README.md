# 1. 목표
도커허브에 올라간 업무인수인계 도커이미지를 쿠버네티스에 올린다.

# 2. 다룰 내용
 - 디플로이와 서비스를 2개 생성한다.
 - Ingress Nginx로 서로 다른 URL prefix로 따른 서비스 라우팅처리를 한다.
 
 참고:
 https://velog.io/@yange/Ingress%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-nginx-ingress-controller
k8s의 Ingress는 Layer 7에서의 요청을 처리할 수 있다.
외부로부터 들어오는 요청에 대한 Load Balancing, TLS/SSL 인증서 처리, 특정 HTTP 경로의 라우팅 등을 Ingress를 통해 자세하게 정의할 수 있다.

# 3. 실행환경 
- 도커 데스크탑에 쿠버네티스 실행가능한 상태에서 실습한다.
- hosts파일에 도메인을 등록한다.
ex) 관리자권한으로 hosts파일에 임의의 도메인을 127.0.0.1로 등록한다.
C:\Windows\System32\drivers\etc\hosts
# To allow the same kube context to work on the host and the container:
127.0.0.1 kubernetes.docker.internal
# End of section

127.0.0.1 bumjin.local
127.0.0.1 registry.local


- Ingress Controller 설치되어 있어야 한다.
호드리 블로그(https://moon1z10.github.io/development/k8s-docker/) 참고 

https://www.youtube.com/watch?v=13y1tWEK2ZY
https://github.com/AnaisUrlichs/ingress-example

```powershell
PS C:\git\hso-takeover-project\deploy\step05> kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   112m
```

```powershell
PS C:\git\hso-takeover-project\deploy\step05> kubectl get svc -n ingress-nginx
NAME                                 TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)                      AGE
ingress-nginx-controller             LoadBalancer   10.103.1.89    localhost     80:30498/TCP,443:30767/TCP   4d2h
ingress-nginx-controller-admission   ClusterIP      10.97.49.122   <none>        443/TCP                      4d2h
```

# 4. 도커 이미지 deploy
이미지명: bumjin/hso-takeover-project:0.0.1-SNAPSHOT (https://hub.docker.com/repository/docker/bumjin/hso-takeover-project/general)


yaml에서 depolyment와 service관련 설정을 --- 로 구분하여 multiple 로 구성한다.

deployment-one.yml
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

deployment-two.yml
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hello
  labels:
    app: hello
spec:
  selector:
    matchLabels:
      app: hello
  template:
    metadata:
      labels:
        app: hello
    spec:
      containers:
      - name: sample-second
        image: docker.io/bumjin/flask-two:1.0
---
apiVersion: v1
kind: Service
metadata:
  name: hello-svc
spec:
  type: ClusterIP
  selector:
    app: hello
  ports:
  - name: http
    port: 8080
```

```powershell
PS C:\git\hso-takeover-project\deploy\step05> kubectl apply -f deployment-one.yaml
deployment.apps/hso-takeover created
service/hso-takeover-svc created
PS C:\git\hso-takeover-project\deploy\step05> kubectl apply -f deployment-two.yaml
deployment.apps/hello created
service/hello-svc created
```

```powershell
PS C:\git\hso-takeover-project\deploy\step05> k get all
NAME                                READY   STATUS    RESTARTS   AGE
pod/hello-6d9ffcccb8-r5pqk          1/1     Running   0          18s
pod/hso-takeover-7f7c7545b7-znv8x   1/1     Running   0          21s

NAME                       TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/hello-svc          ClusterIP   10.98.154.12    <none>        8080/TCP   18s
service/hso-takeover-svc   ClusterIP   10.110.75.160   <none>        8080/TCP   21s
service/kubernetes         ClusterIP   10.96.0.1       <none>        443/TCP    130m

NAME                           READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/hello          1/1     1            1           18s
deployment.apps/hso-takeover   1/1     1            1           22s

NAME                                      DESIRED   CURRENT   READY   AGE
replicaset.apps/hello-6d9ffcccb8          1         1         1       18s
replicaset.apps/hso-takeover-7f7c7545b7   1         1         1       21s
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
      - backend:
          service:
            name: hello-svc
            port:
              number: 8080
        path: /two
        pathType: Prefix

```
bumjin.local 도메인으로 접속하면 defaultBackend 서비스인 hso-takeover-svc로 라우팅된다.
url에 /one path로 접속해도 hso-takeover-svc로 라우팅된다.
url에 /two path로 접속하면 hello-svc로 라우팅된다.

http://bumjin.local/
혹은
http://bumjin.local/one 로 접속
http://bumjin.local/two 로 접속


# Deployment,Service 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step05> kubectl delete -f deployment-one.yaml
deployment.apps "hso-takeover" deleted
service "hso-takeover-svc" deleted
PS C:\git\hso-takeover-project\deploy\step05> kubectl delete -f deployment-two.yaml
deployment.apps "hello" deleted
service "hello-svc" deleted
```

# Ingress 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step05> kubectl delete -f ingress.yaml       
ingress.networking.k8s.io "simple-example" deleted
```