# Deploy Spring Boot application on Kubernetes cluster (minikube)

## [Click here to watch the video for demonstration.](https://youtu.be/M6ZavWvKlcc)


### Prerequisites:
 - [Docker](https://docs.docker.com/engine/install/) or [Docker alternative - Colima](https://github.com/abiosoft/colima)
 - [Minikube](https://minikube.sigs.k8s.io/docs/start/)
 - [Helm](https://helm.sh/docs/intro/install/)
 - [kubectl](https://kubernetes.io/docs/tasks/tools/)
 - IDE and JDK


## Perform the following steps to deploy spring boot app on Minikube :

### If you want to start from the scratch without cloning this repository, then perform following steps:

1. Create project using https://start.spring.io/ with name "springboot-on-minikube"
2. Then add sample controller for creating rest api
3. From the terminal cd into your project directory and build project using ``` ./gradlew clean build ```
4. Start docker on the system
5. Build docker image using : ```  docker build -t springboot-on-minikube .  ```  [Command reference](https://minikube.sigs.k8s.io/docs/drivers/docker/)
6. Run the docker container: ``` docker run -p 8080:8080 springboot-on-minikube ``` and verify calling url ``` localhost:8080/data ```
7. Now stop the container service and we will create helm chart.
8. Run command to create helm chart :  ``` helm create ytkubechart ```
9. To see helm directory structure : ``` tree ytkubechart ```
10. Then do following changes in values.yaml file
 ```
    image:
      repository: springboot-on-minikube
      pullPolicy: IfNotPresent
      # Overrides the image tag whose default is the chart appVersion.
      tag: "latest"
 ```

11. Change service to NodePort in values.yaml file
    ```
    service:
        type: NodePort
        port: 8080
    ```

    and comment following in deployment.yaml file

    ```
      #          livenessProbe:
      #            httpGet:
      #              path: /
      #              port: http
      #          readinessProbe:
      #            httpGet:
      #              path: /
      #              port: http

    ```    
13. Now go into terminal and start minikube using ``` minikube start --driver=docker ```
14. Enable docker env using command :  ``` eval $(minikube docker-env)  ```  [Command Reference](https://minikube.sigs.k8s.io/docs/commands/docker-env/)
15. Build docker image in minikube : ``` docker build -t springboot-on-minikube . ```
16. To see images : ``` minikube image ls ```
17. Next install and deploy helm chart on cluster : ``` helm install mychart ytkubechart ```
18. Verify the configuration using commands like
    ```
      kubectl get pods
      kubectl get service
    ```
19. To access api, run command : ``` minikube service mychart-ytkubechart --url ```   [Command Reference](https://minikube.sigs.k8s.io/docs/handbook/accessing/#using-minikube-service-with-tunnel)
20. Copy url returned by the command mentioned in step : 18 and call that url from browser.
21. To enable ingress on minikube run following command :  ``` minikube addons enable ingress ```  [Command reference 1](https://kubernetes.io/docs/tasks/access-application-cluster/ingress-minikube/)  [Ref 2](https://minikube.sigs.k8s.io/docs/handbook/addons/ingress-dns/)
22. Verify ingress controller is running :  ``` kubectl get pods -n ingress-nginx ```
23. Now do ingress configuration changes in values.yaml
    ```
      service:
        type: ClusterIP
        port: 8080
    ```

    ```
      ingress:
        enabled: true
        className: ""
        annotations: {}
          # kubernetes.io/ingress.class: nginx
          # kubernetes.io/tls-acme: "true"
        hosts:
          - host: ytlecture.com
            paths:
              - path: /
                pathType: Prefix
        tls: []
    ```
24. Update version in Chart.yaml file
    ```
     version: 0.1.2
    ```
25. Now to update our kubernetes configuration, run command in terminal : ``` helm upgrade mychart ytkubechart ```
26. Verify ingress service using : ``` kubectl get ingress ```
27. Next add entry in ``` /etc/hosts ``` file
     - run ``` sudo vi /etc/hosts ```
     - enter password
     - press ``` i ``` key on keyboard
     - add entry : ``` 127.0.0.1  ytlecture.com ```
     - press ``` Esc ``` key on keyboard
     - press ``` :wq ```
       
27. verify changes using ``` cat /etc/hosts ```
28. Enable tunnel running command : ``` minikube tunnel ```
29. Then call url in browser : http://ytlecture.com/data
30. Hurray :clap:  you have successfully deployed your first spring rest api on Kubernetes.
31. Stop minikube using : ``` minikube stop ```     


### If you want to start by cloning this repository, then perform following steps:
1. From the terminal cd into your project directory and build project using ``` ./gradlew clean build ```
2. Start docker on system
3. Build docker image using : ```  docker build -t springboot-on-minikube .  ```
4. Run the docker container: ``` docker run -p 8080:8080 springboot-on-minikube ``` and verify calling url ``` localhost:8080/data ```
5. Now stop the container service 
6. start minikube using ``` minikube start --driver=docker ```
7. Enable docker env using command :  ``` eval $(minikube docker-env)  ```  [Command Reference](https://minikube.sigs.k8s.io/docs/commands/docker-env/)
8. Build docker image in minikube : ``` docker build -t springboot-on-minikube . ```
9. To see images : ``` minikube image ls ```
10. To enable ingress on minikube run following command :  ``` minikube addons enable ingress ```  [Command reference 1](https://kubernetes.io/docs/tasks/access-application-cluster/ingress-minikube/)  [Ref 2](https://minikube.sigs.k8s.io/docs/handbook/addons/ingress-dns/)
11. Verify ingress controller is running :  ``` kubectl get pods -n ingress-nginx ```
12. Next install and deploy helm chart on cluster : ``` helm install mychart ytkubechart ```
13. Verify ingress service using : ``` kubectl get ingress ```
14. Next add entry in ``` /etc/hosts ``` file
     - run ``` sudo vi /etc/hosts ```
     - enter password
     - press ``` i ``` key on keyboard
     - add entry : ``` 127.0.0.1  ytlecture.com ```
     - press ``` Esc ``` key on keyboard
     - press ``` :wq ```
15. verify changes using ``` cat /etc/hosts ```
16. Enable tunnel running command : ``` minikube tunnel ```
17. Then call url in browser : http://ytlecture.com/data
18. Hurray :clap:  you have successfully deployed your first spring rest api on Kubernetes.
19. Stop minikube using : ``` minikube stop ``` 

    
