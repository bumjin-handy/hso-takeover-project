# 1. 목표
도커허브에 올라간 업무인수인계 도커이미지를 쿠버네티스에 올린다.

# 2. 다룰 내용
 - POD 생성하고 Deploy한다.
 - 브라우저 접속을 위한 POD 서비스 노출은 kubectl expose를 통해 노출한다.

# 3. 실행환경 
- 도커 데스크탑에 쿠버네티스 실행가능한 상태에서 실습한다.


```powershell
PS C:\git\hso-takeover-project\deploy\step01> kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   31m
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
PS C:\git\hso-takeover-project\deploy\step01> kubectl apply -f deployment-hstakeover.yml
deployment.apps/hso-takeover created
```

```powershell
PS C:\git\hso-takeover-project\deploy\step01> kubectl get all
NAME                                           READY   STATUS    RESTARTS   AGE
pod/hstakeover-deployment-64454944cb-4frlw   1/1     Running   0          28s

NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   55m

NAME                                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/hstakeover-deployment   1/1     1            1           28s

NAME                                                 DESIRED   CURRENT   READY   AGE
replicaset.apps/hstakeover-deployment-64454944cb   1         1         1       28s
```

서비스는 현재 노출되어 있지 않아 브라우저로 접속 불가


## 외부로 서비스 노출
- NodePort 방식으로 서비스 노출

```powershell
PS C:\git\hso-takeover-project> kubectl expose deployment.apps/hstakeover-deployment --type="NodePort" --port 8080
```

## 서비스가 생성됨
```powershell
PS C:\git\hso-takeover-project\deploy\step01> kubectl get svc
NAME                      TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)          AGE
hstakeover-deployment   NodePort    10.98.32.44   <none>        8080:31604/TCP   6s
kubernetes                ClusterIP   10.96.0.1     <none>        443/TCP          60m
```
이 서비스는 NodePort 타입으로, 클러스터의 각 노드에 31604 포트를 열어 외부에서 접근할 수 있게한다.
내부적으로는 8080 포트로 트래픽을 전달한다.

## 노드포트로 접속
http://localhost:31604/

# 서비스 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step01> kubectl delete service hstakeover-deployment
service "hstakeover-deployment" deleted   
```

# Deployment 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step01> kubectl delete -f hstakeover-deployment.yml
deployment.apps "hstakeover-deployment" deleted
```

# 혹은 한방에 deployment와 service 삭제
```powershell
PS C:\git\hso-takeover-project\deploy\step01> kubectl delete --all deployments,service
deployment.apps "hstakeover-deployment" deleted
service "hstakeover-deployment" deleted
service "kubernetes" deleted
```