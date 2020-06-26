
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
ansible-playbook computate_zookeeper.yml -i /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME/hosts --vault-id @prompt
```

Run the playbook to install the computate-medicale project for development. 

```bash
ansible-playbook computate_medicale.yml -i /usr/local/src/computate-ansible/inventories/$USER-$HOSTNAME/hosts --vault-id @prompt
```

# Start the development project in English. 

## Maven build the computate-medicale project. 

```bash
cd /usr/local/src/computate-medicale
mvn clean insta..
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
ansible-playbook computate_zookeeper_openshift.yml -i /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts --vault-id @prompt
```

Run the playbook to install a Red Hat SSO server in your OpenShift environment. 

```bash
ansible-playbook redhat_sso_openshift.yml -i /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts --vault-id @prompt
```

Run the playbook to install the computate-medicale project in your OpenShift environment. 

```bash
ansible-playbook computate_medicale_openshift.yml -i /usr/local/src/computate-ansible/inventories/$USER-openshift/hosts --vault-id @prompt
```

