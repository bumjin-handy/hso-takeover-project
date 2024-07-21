# 1. 목표
도커허브에 올라간 업무인수인계 도커이미지를 쿠버네티스에 올린다.

# 2. 다룰 내용
 - POD 생성하고 Deploy한다.
 - LoadBalancer방식으로 서비스를 생성, 노출한다.
 
# 3. 실행환경 
- 도커 데스크탑에 쿠버네티스 실행가능한 상태에서 실습한다.
- Ingress Controller 설치되어 있어야 한다.
호드리 블로그(https://moon1z10.github.io/development/k8s-docker/) 참고 


```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   31m
```

```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl get svc -n ingress-nginx

NAME                                 TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)                      AGE
ingress-nginx-controller             LoadBalancer   10.103.1.89    localhost     80:30498/TCP,443:30767/TCP   2d20h
ingress-nginx-controller-admission   ClusterIP      10.97.49.122   <none>        443/TCP                      2d20h
```

# 4. 도커 이미지 deploy
이미지명: bumjin/hso-takeover-project:0.0.1-SNAPSHOT (https://hub.docker.com/repository/docker/bumjin/hso-takeover-project/general)

```yaml
apiVersion: apps/v1	
kind: Deployment
metadata:	
  name: hstakeover-deployment
spec:	
  replicas: 1	
  selector:	
    matchLabels:
      app.kubernetes.io/name: hstakeover-deployment
  template:
    metadata:	
      labels:	
         app.kubernetes.io/name: hstakeover-deployment
    spec:
      containers:
      - name: hso-takeover	
        image: bumjin/hso-takeover-project:0.0.1-SNAPSHOT
```

```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl apply -f deployment-hstakeover.yml
deployment.apps/hso-takeover created
```

```powershell
S C:\git\hso-takeover-project\deploy\step03> kubectl get all
NAME                                           READY   STATUS    RESTARTS   AGE
pod/hstakeover-deployment-64454944cb-fn59m   1/1     Running   0          12s

NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   5m58s

NAME                                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/hstakeover-deployment   1/1     1            1           12s

NAME                                                 DESIRED   CURRENT   READY   AGE
replicaset.apps/hstakeover-deployment-64454944cb   1         1         1       12s
```


# LoadBalancer 방식으로 서비스 노출
```yaml
apiVersion: v1
kind: Service
metadata:
  name: hstakeover-svc
spec:
  selector:
    app.kubernetes.io/name: hstakeover-deployment
  type: LoadBalancer
  ports:
  - protocol: TCP
    port: 8080
    targetPort: 8080
```


```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl apply -f service-hstakeover-loadbalancer.yml
service/hstakeover-svc created
```


```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl get svc
NAME                       TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
hstakeover-svc   LoadBalancer   10.105.186.137   localhost     8080:31574/TCP   2m52s
kubernetes                 ClusterIP      10.96.0.1        <none>        443/TCP          7m26s
```
이 서비스는 LoadBalancer 타입으로, EXTERNAL-IP localhost의 8080포트로 
내부적으로는 31574 노드포트로 트래픽을 전달한다.

## 노드포트로 접속
http://localhost:8080/

# Service 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl delete -f service-hstakeover-loadbalancer.yml
service "hstakeover-svc" deleted
```

# Deployment 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl delete -f deployment-hstakeover.yml
deployment.apps "hstakeover-deployment" deleted
```

# 혹은 한방에 deployment와 service 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl delete --all deployments,service
deployment.apps "hstakeover-deployment" deleted
service "hstakeover-svc" deleted
service "kubernetes" deleted
```

# 참고 service-hstakeover-loadbalancer 에서 port: 8080를 사용한 이유
```yaml
ports:
  - protocol: TCP
    port: 8080
    targetPort: 8080
```
port: 8080를 80으로 했을때, EXTERNAL-IP가 <pending>  으로 나와 정상적으로 동작하지 않는다.
```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl apply -f service-hstakeover-loadbalancer.yml
service/hstakeover-svc created

pod/hstakeover-deployment-64454944cb-srpvb   1/1     Running   0          14s
NAME                               TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)        AGE
service/hstakeover-svc   LoadBalancer   10.105.186.137   <pending>     80:31574/TCP   27s
service/kubernetes                 ClusterIP      10.96.0.1        <none>        443/TCP        5m1s
NAME                                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/hstakeover-deployment   1/1     1            1           14s

replicaset.apps/hstakeover-deployment-64454944cb   1         1         1       14s
```

추측은 이미 ingress-nginx-controller 에서 기본적으로 80으로 서비스되는게 있기 때문인거 같다.
```powershell
PS C:\git\hso-takeover-project\deploy\step03> kubectl get svc -n ingress-nginx
NAME                                 TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)                      AGE
ingress-nginx-controller             LoadBalancer   10.103.1.89    localhost     80:30498/TCP,443:30767/TCP   2d21h
ingress-nginx-controller-admission   ClusterIP      10.97.49.122   <none>        443/TCP                      2d21h
```