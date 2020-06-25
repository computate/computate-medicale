
Link to 3-minute video that describes and demonstrates your solution
This question is required. *
A demo of your project uploaded to Box and made visible to Red Hatters.
Please double check the permissions! Inaccessible links will disqualify your submission!

Long description
About 500 words, or about one page of text, that covers the solution in more detail

Roadmap
A document or image that shows how mature your solution is today and how you plan to improve it in the future

### You can watch a video and learn more about this project here: https://devpost.com/software/computate-medicale

# Prerequisites

## What is the first step to creating my own website?
### Choose a domain name. 
https://www.computate.org/enUS/course/001/001-choose-domain-name

## What can I do once I have purchased a domain name?
### Obtain a valid TLS certificate for free, for security and credibility. 
https://www.computate.org/enUS/course/001/008-how-to-obtain-free-tls-certificates

## Where can I host the project online? 
### Red Hat OpenShift Online is the very best open source cloud hosting available. 
https://www.openshift.com/products/online/

# Installation

The installation of the project for both development and production in containers is completely automated with Ansible. 
Begin by installing both the ansible and python3 packages. 

```bash
sudo yum install -y ansible python3
```

If you have an older operating system that does not yet support python3, you may struggle to deploy the application on OpenShift in the cloud. The OpenShift Ansible modules seem to require python3 as the system library, so I recommend updating your operating system to something more recent, for example CentOS8 or RHEL8. You might also be able to update your ansible configuration to make it work: 

```
sudo vim /etc/ansible/ansible.cfg
```

```
[defaults]
interpreter_python=/usr/bin/python3
```

## Development installation of computate-medicale

```bash

# Create a directory for the computate project containing the ansible scripts to run. 
sudo install -d -o $USER -g $USER /usr/local/src/computate

# Change to the computate ansible directory. 
cd /usr/local/src/computate/ansible

# Run the playbook to install the computate-medicale project for development. 
ansible-playbook computate_medicale.yml -i /usr/local/src/my-ansible-project/inventories/my-ansible-inventory/hosts --vault-id @prompt
```

# Démarrer le projet en français : 

## If you wish to contribute to the project, you will want to configure it in French. 

The project is entirely written in French as a first language, and English as a second language. 
This ensures proper internationalization is in place through the whole project! 

## Eclipse Debug Configuration

Main Project: computate-medicale

Main class: org.computate.medicale.frFR.vertx.AppliVertx

Environment Variables: 
* configChemin: /usr/local/src/computate-medicale/config/computate-medicale.config
* zookeeperNomHote: localhost
* zookeeperPort: 10281

# Configurer les données

```bash

createuser computate -P
psql -c "create database medicale_frfr; "
psql -c "grant all privileges on database medicale_frfr to computate; "

/srv/solr-7.1.0/bin/solr create_collection -c medicale_frfr -n computate

```

## computate-medicale.config

configChemin: /usr/local/src/computate-medicale/config/computate-medicale.config

```ini
appliNom = computate-medicale

[computate-medicale]
zookeeperNomHote=localhost
zookeeperPort=10281
langueNom=frFR
appliChemin_enUS=/usr/local/src/computate-medicale
appliChemin_frFR=/usr/local/src/computate-medicale
nomDomaine=computate.org
nomEnsembleDomaine=org.computate.medicale
autresLangues=enUS
suffixeSrcMainJava=/src/main/java
suffixeSrcGenJava=/src/gen/java
cheminsRelatifsARegarder=src/main/java/org/computate/medicale/frFR
jdbcUrl="jdbc:postgresql://localhost:5432/medicale_frfr"
jdbcUtilisateur=computate
jdbcMotDePasse="..."
siteNomHote=dev.computate.org
siteUrlBase=https://dev.computate.org:10180
authRoyaume=COMPUTATE.ORG
authRessource=computate.org
authSecret=...
authUrl=https://sso.computate.org/auth
authSslRequis=all
sslJksChemin=/srv/heytate.com/server.jks
sslJksMotDePasse="..."
sitePort=10180
solrUrl=http://localhost:10383/solr/medicale_frfr
apiContactMail=ctate@redhat.com
siteEcrireMethodes=html
siteEcrireMethodes=htmlMeta
siteEcrireMethodes=htmlScripts
siteEcrireMethodes=htmlScript
siteEcrireMethodes=htmlStyles
siteEcrireMethodes=htmlStyle
siteEcrireMethodes=htmlBody
siteZone=America/Denver

```

# Start the project in English : 

## Eclipse Debug Configuration

Main Project: computate-medicale

Main class: org.computate.medicale.enUS.vertx.AppVertx

Environment Variables: 
* configPath: /usr/local/src/computate-medicale/config/computate-medicale-enUS.config
* zookeeperHostName: localhost
* zookeeperPort: 10281

# Configure the data

```bash

createuser computate -P
psql -c "create database medicale_enus; "
psql -c "grant all privileges on database medicale_enus to computate; "

/srv/solr-7.1.0/bin/solr create_collection -c medicale_enus -n computate

```

## computate-medicale-enUS.config

configPath: /usr/local/src/computate-medicale/config/computate-medicale-enUS.config

```ini
appName = computate-medicale

[computate-medicale]
zookeeperHostName=localhost
zookeeperPort=10281
languageName=frFR
appPath_enUS=/usr/local/src/computate-medicale
appPath_frFR=/usr/local/src/computate-medicale
domainName=computate.org
domainPackageName=org.computate.medicale
otherLanguages=enUS
jdbcUrl="jdbc:postgresql://localhost:5432/medicale_enus"
jdbcUser=computate
jdbcPassword="..."
siteHostName=dev.computate.org
siteBaseUrl=https://dev.computate.org:10380
authRealm=COMPUTATE.ORG
authResource=computate.org
authSecret=...
authUrl=https://sso.computate.org/auth
authSslRequired=all
sslJksPath=/srv/heytate.com/server.jks
sslJksPassword="..."
sitePort=10380
solrUrl=http://localhost:10383/solr/medicale_enus
apiContactEmail=ctate@redhat.com
siteZone=America/Denver

```

# Deployment
Sample OpenShift deployment yml file to be placed in /usr/local/src/computate-medicale/src/main/fabric8/deployment.yml: 

```yaml
spec:
  strategy:
    activeDeadlineSeconds: 21600
    recreateParams:
      timeoutSeconds: 3600
    resources: {}
    type: Recreate
  template:
    spec:
      containers:
        - name: vertx
          env:
            - name: KUBERNETES_NAMESPACE
              valueFrom:
                fieldRef:
                  apiVersion: v1
                  fieldPath: metadata.namespace
            - name: JAVA_OPTIONS
              value: '-Dvertx.cacheDirBase=/tmp -Dvertx.jgroups.config=default'
            - name: langueNom
              value: frFR
              
            - name: zookeeperHostName
              value: ...
              
            - name: zookeeperPort
              value: 8080
              
            - name: clusterPublicPort
              value: 8081
              
            - name: appliChemin_enUS
              value: /usr/local/src/computate-medicale
              
            - name: domainName
              value: computate.org
              
            - name: companyName
              value: computate.org
              
            - name: jdbcUrl
              value: "jdbc:postgresql://...:5432/medicale_enus"
              
            - name: jdbcUsername
              value: computate
              
            - name: jdbcPassword
              value: "..."
              
            - name: siteHostName
              value: school.computate.org
              
            - name: siteBaseUrl
              value: https://school.computate.org
              
            - name: authRealm
              value: COMPUTATE.ORG
              
            - name: authResource
              value: computate.org
              
            - name: authSecret
              value: ...
              
            - name: authUrl
              value: https://sso.computate.org/auth
              
            - name: authSslRequired
              value: all
              
            - name: sslJksPath
              value: /srv/heytate.com/server.jks
              
            - name: sslJksPassword
              value: "..."
              
            - name: sitePort
              value: 8080
              
            - name: solrUrl
              value: http://...:8080/solr/medicale_enUS
              
            - name: apiContactMail
              value: ...
              
            - name: staticBaseUrl
              value: https://computate.neocities.org/medicale
              
            - name: numberExecutors
              value: 5
              
            - name: siteZone
              value: America/New_York

          readinessProbe:
            httpGet:
              path: /health
              port: 8080
              scheme: HTTP
            initialDelaySeconds: 15
            timeoutSeconds: 5
          livenessProbe:
            httpGet:
              path: /health
              port: 8080
              scheme: HTTP
            initialDelaySeconds: 15
            timeoutSeconds: 5
          ports:
          - containerPort: 8080
            name: http
            protocol: TCP
          - containerPort: 8081
            name: cluster
            protocol: TCP
          - containerPort: 9779
            name: prometheus
            protocol: TCP
          - containerPort: 8778
            name: jolokia
            protocol: TCP

```

# Deploy static files to CDN. 

```bash
rsync -r /usr/local/src/computate-medicale-static/ /usr/local/src/computate.org-static/medicale/
```

# Certs

```bash
sudo yum install -y letsencrypt
curl https://letsencrypt.org/certs/isrgrootx1.pem.txt -o /usr/local/src/computate-medicale/config/root.crt
curl https://letsencrypt.org/certs/lets-encrypt-x3-cross-signed.pem.txt -o /usr/local/src/computate-medicale/config/ca1.crt
curl https://letsencrypt.org/certs/letsencryptauthorityx3.pem.txt -o /usr/local/src/computate-medicale/config/ca2.crt
sudo certbot -d demo.heytate.com --manual --preferred-challenges dns certonly --server https://acme-v02.api.letsencrypt.org/directory
sudo cp /etc/letsencrypt/live/demo.heytate.com/privkey.pem /usr/local/src/computate-medicale/config/server.key
sudo cp /etc/letsencrypt/live/demo.heytate.com/fullchain.pem /usr/local/src/computate-medicale/config/server.crt
sudo chown $USER: /usr/local/src/computate-medicale/config/server.* /usr/local/src/computate-medicale/config/root.crt
cat /usr/local/src/computate-medicale/config/root.crt /usr/local/src/computate-medicale/config/server.crt > /usr/local/src/computate-medicale/config/merged.crt
openssl pkcs12 -export -in /usr/local/src/computate-medicale/config/merged.crt -inkey /usr/local/src/computate-medicale/config/server.key -out /usr/local/src/computate-medicale/config/server.p12 -name demo.heytate.com
keytool -importkeystore -srckeystore /usr/local/src/computate-medicale/config/server.p12 -destkeystore /usr/local/src/computate-medicale/config/server.jks -srcstoretype pkcs12 -deststoretype pkcs12 -alias demo.heytate.com -destalias demo.heytate.com
rm /usr/local/src/computate-medicale/config/server.jceks
keytool -genseckey -alias demo.heytate.com -storetype JCEKS -keystore /usr/local/src/computate-medicale/config/server.jceks
# Reconfigure the Red Hat SSO secret:
base64 /usr/local/src/computate-medicale/config/server.jks | perl -pe'chomp'
base64 /usr/local/src/computate-medicale/config/server.jceks | perl -pe'chomp'
# Configure the www.computate.org route:
cat /usr/local/src/computate-medicale/config/server.crt
cat /usr/local/src/computate-medicale/config/server.key
cat /usr/local/src/computate-medicale/config/ca1.crt
```

# Deployment to OpenShift

```bash
cd /usr/local/src/computate-medicale
mvn clean install
mvn fabric8:deploy -Popenshift
```
