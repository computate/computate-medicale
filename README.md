
# Description

My name is Christopher Tate, and I love to create systems for people to use in many places to solve things. Like a heart clinic who would love to have custom software that works with cardiac data in a more specialized way than their larger Electronic Medical Records solution. I also wanted to build some software to help a medical clinic identify their own patients who may be in most need of caution during this time of COVID-19. For example, those with pre-existing cardiovascular, lung, hypertension, metabolic, asthma conditions which the Center For Disease Control reports here may be more likely to be hospitalized during this time. 

https://gis.cdc.gov/grasp/COVIDNet/COVID19_5.html

So I setup an OpenShift Online environment to deploy my applications in the cloud. 

* I Deployed PostgreSQL database, Apache Zookeeper cluster manager, Apache Solr search engine, and Red Hat SSO. 
* I created a github project called computate-medicale to build a Vert.X application to solve the problem. 

Here is the complete OpenApi 3 Swagger specification: 

https://github.com/computate/computate-medicale/blob/master/src/main/resources/openapi3-enUS.yaml

* Then I deployed the computate-medicale application to OpenShift. 

I created a set of sample patient data for an example clinic and imported that data into my application through its own secured APIs using Ansible. 

Behind every page on the site is a powerful JSON REST API. It's powered by the search engine. Because we use a search engine with our data, we can generate powerful analytics to help us solve COVID-19 related problems. I built some dashboards for a clinic to gain insight into their patients most likey affected by COVID-19. We add relevance to our data based on the graphs I showed earlier from the CDC. 

Thanks to the search engine, we can score patients medical conditions, and filter through less relevant conditions like those patients with none: patientMedicalConditions_text_enUS:(*)

If we add a score to certain keywords in a patients medical conditions, we can norrow in on patients most in need of help at this time: 

* We will give asthma a factor of 4: asthma^=4'
* We will give neurologic conditions a factor of 4: nerves^=4 brain^=4 nervous^=4
* We will give respiratory conditions a factor of 4: lung^=4 respiratory^=4 airway^=4
* We will give immune conditions a factor of 3: immune^=3
* We will give heart conditions a factor of 3: heart^=2
* And stomach conditions a factor of 3: stomach^=1 intestine^=1

Now we see how the analytics have changed to focus in on COVID-19 targeted conditions, and here is the list of patients of most concern during this time sorted by score. Like those with combined asthma, airway and lung concerns: 

   Score Name Conditions
1. 8.0   Doug     "Reactive airway disease  asthma "
2. 8.0   Karin    asthma - premature lungs
3. 7.0   Jeff     "food allergy  asthma"
4. 7.0   Jeff     "food allergy  asthma  eczema"
5. 7.0   Yves     "Penicillin allergy  asthma "
6. 7.0   Adam     Seasonal allergies and allergy related asthma.
7. 7.0   Samantha "Severe Milk Allergy  asthma "
8. 7.0   Samantha "Severe Milk Allergy  asthma "
9. 5.0   Michelle "Nut allergy  heart arrhythmia"
10. 4.0  Beck     Asthma

Like that, any medical clinic can extend this open source software to solve their own challenges with data analytics during the pandemic. 



### You can watch a video and learn more about this project here: 

11 min version: https://youtu.be/Y1YZZSXGc8s

3 min version: https://youtu.be/drtUb1UfmKw

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

## Ansible on older operating systems. 

If you have an older operating system that does not yet support python3, you may struggle to deploy the application on OpenShift in the cloud. The OpenShift Ansible modules seem to require python3 as the system library, so I recommend updating your operating system to something more recent, for example CentOS8 or RHEL8. 

On older operating systems, to deploy the development applications you might want to configure ansible for python2. 

To deploy to OpenShift, you will want to configure ansible to point to python3. 

You might update your ansible configuration like this to make it work: 

```
sudo vim /etc/ansible/ansible.cfg
```

```
[defaults]
interpreter_python=/usr/bin/python3
```
## Ansible training. 

For training on ansible and automation, I recommend the following Red Hat course. 
By completing the course and taking the exam, you can be a Certified Specialist in Ansible Automation. 

https://www.redhat.com/en/services/training/do407-automation-ansible-i

## Development installation of computate-medicale. 

### Create an ansible inventory for development. 

You will want to create your own directory to store your ansible inventories for both development and production in the cloud. 

Create a directory for your ansible scripts. 

```bash
sudo install -d -o $USER -g $USER /usr/local/src/computate-ansible
```

Create a directory for your development inventory. 

```bash
install -d /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME
```

Create a hosts file for your development inventory. 

```bash
echo 'localhost' > /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME/hosts
```

### Create an ansible vault for the application secrets. 

Create an encrypted ansible vault with a password for the host secrets for your development inventory. 

```bash
ansible-vault edit /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME/host_vars/$HOSTNAME/vault
```

The contents of the vault will contain the secrets needed to override any default values you want to change in the computate-medicale defaults defined here. 

https://github.com/computate/computate/blob/master/ansible/roles/computate_medicale/defaults/main.yml

There are descriptions for each of the fields. 
There are several sections of fields, including: 

* computate-medicale system defaults
* Ansible defaults
* Zookeeper defaults
* Solr defaults
* PostgreSQL defaults
* computate-medical global defaults
* computate-medicale France French defaults
* computate-medicale US English defaults
* SMTP defaults
* OpenID Connect auth server defaults
* SSL/TLS defaults

### Clone the computate project to run the ansible scripts. 

Create a directory for the computate project containing the ansible scripts to run. 

```bash
sudo install -d -o $USER -g $USER /usr/local/src/computate
```

Clone the computate project. 

```bash
git clone https://github.com/computate/computate.git /usr/local/src/computate
```

Change to the computate ansible directory. 

```bash
cd /usr/local/src/computate/ansible
```

Run the playbook to install a PostgreSQL server on your development computer. 

```bash
ansible-playbook computate_postgres.yml -i /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME/hosts --vault-id @prompt
```

Run the playbook to install a Zookeeper cluster manager on your development computer. 

```bash
ansible-playbook computate_zookeeper.yml -i /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME/hosts --vault-id @prompt
```

Run the playbook to install a Solr search engine on your development computer. 

```bash
ansible-playbook computate_solr.yml -i /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME/hosts --vault-id @prompt
```

Run the playbook to install the computate-medicale project for development. 

```bash
ansible-playbook computate_medicale.yml -i /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME/hosts --vault-id @prompt
```

# Start the development project in English. 

## Maven build the computate-medicale project. 

```bash
cd /usr/local/src/computate-medicale
mvn clean install
```

## Make sure the Eclipse Marketplace and Git integration are installed. 

Help -> Install

Work with: Oxygen - http://download.eclipse.org/releases/oxygen

Or whatever your eclipse release is. 

* General Purpose Tools -> Marketplace Client
* Collaboration -> Git integration for Eclipse

## Install the Eclipse maven plugin. 

Install from Marketplace "Maven Integration for Eclipse"

## Import the computate-medicale project into Eclipse. 

File -> Import... -> Maven -> Existing Maven Projects

Click [ Next > ]

Root Directory: /usr/local/src/computate-medicale

Click [ Finish ]

## Eclipse Debug Configuration. 

Main Project: computate-medicale

Main class: org.computate.medicale.enUS.vertx.AppVertx

Environment Variables: 

* configPath: /usr/local/src/computate-medicale/config/computate-medicale-enUS.config
* zookeeperHostName: localhost
* zookeeperPort: 2181

# Deploy computate-medicale in US English to OpenShift. 

### Create an ansible inventory for production. 

You will want to create your own directory to store your ansible inventories for production in the cloud. 

Create a directory for your ansible scripts. 

```bash
sudo install -d -o $USER -g $USER /usr/local/src/computate-ansible
```

Create a directory for your production inventory. 

```bash
install -d /usr/local/src/computate-ansible/inventories/$USER-openshift
```

Create a hosts file for your production inventory. 

```bash
echo 'localhost' > /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts
```

### Create an ansible vault for the application secrets. 

Create an encrypted ansible vault with a password for the host secrets for your production inventory. 

```bash
ansible-vault edit /usr/local/src/computate-ansible/inventories/$USER-openshift/host_vars/localhost/vault
```

The contents of the vault will contain the secrets needed to override any default values you want to change in the computate-medicale defaults defined here. 

https://github.com/computate/computate/blob/master/ansible/roles/computate_medicale_openshift_enUS/defaults/main.yml

There are descriptions for each of the fields. 
There are several sections of fields, including: 

* Ansible defaults
* Zookeeper defaults
* Solr defaults
* PostgreSQL defaults
* computate-medical global defaults
* computate-medicale US English defaults
* SMTP defaults
* SSL/TLS defaults
* OpenID Connect auth server defaults

Change to the computate ansible directory. 

```bash
cd /usr/local/src/computate/ansible
```

Run the playbook to install a PostgreSQL server in your OpenShift environment. 

```bash
ansible-playbook postgres_openshift.yml -i /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts --vault-id @prompt
```

Run the playbook to install a Zookeeper cluster manager in your OpenShift environment. 

```bash
ansible-playbook computate_zookeeper_openshift.yml -i /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts --vault-id @prompt
```

Run the playbook to install a Solr search engine in your OpenShift environment. 

```bash
ansible-playbook computate_solr_openshift.yml -i /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts --vault-id @prompt
```

Run the playbook to install a Red Hat SSO server in your OpenShift environment. 

```bash
ansible-playbook redhat_sso_openshift.yml -i /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts --vault-id @prompt
```

Run the playbook to install the computate-medicale project in your OpenShift environment. 

```bash
ansible-playbook computate_medicale_openshift.yml -i /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts --vault-id @prompt
```

