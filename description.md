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

