
# Be sure to be signed into both openshift accounts. 
# Be sure to log out of your application. 
# Be sure to delete the OpenShift project. 
# Copy the ansible password to the clipboard. 

function computate() {
	echo
	read -p '' -i '' ACTION_COMMAND
	case $ACTION_COMMAND in 
		[Ss]* );;
		[Qq]* ) exit 0 ;;
		* )
		eval "$1"
		;;
	esac
}

computate "gio open 'https://books.google.com/books?id=D4gzDAAAQBAJ&pg=PA53&lpg=PA53&dq=Open+is+an+opportunity,+a+chance+to+broaden+the+mind,+free+tools+and+resources+to+benefit+all+of+mankind.+Open+is+an+aperture,+something+you+look+through,+access+for+all,+not+just+the+few.&source=bl&ots=6_mG4PsIK1&sig=ACfU3U3Dn2Qx7bZuuoN3tnwDCj8oFRQMPQ&hl=en&sa=X&ved=2ahUKEwiPqYbZnoPlAhU2CjQIHXgzBwEQ6AEwAXoECAkQAQ';\
echo 'Open is an opportunity, a chance to broaden the mind, free tools and resources to benefit all of mankind. Open is an aperture, something you look through, access for all, not just the few. '"

computate "echo 'My name is Christopher Tate, and I love to create systems for people to use in many places to solve things. '"

computate "gio open 'http://idahoheartinstitute.com/';\
echo 'Like this heart clinic who would love to have custom software that works with cardiac data in a more specialized way than their larger Electronic Medical Records solution. '"

computate "gio open 'https://gis.cdc.gov/grasp/COVIDNet/COVID19_5.html';\
echo 'I also wanted to build some software that help a medical clinic identify their own patients who may be in most need of caution during this time of COVID-19. For example, those with pre-existing cardiovascular, lung, hypertension, metabolic, asthma conditions which the Center For Disease Control reports here may be more likely to be hospitalized during this time. '"

computate "gio open 'https://console.rh-us-east-1.openshift.com/console/catalog';\
echo 'So I setup an OpenShift Online environment to deploy my applications in the cloud. '"

computate "echo 'I created a medical project in OpenShift. '"

computate "echo 'I deployed a PostgreSQL database secret, volume claim, service, and deployment config to OpenShift using ansible automation. ';\
(cd /usr/local/src/computate/ansible && ansible-playbook postgres_openshift.yml -i inventories/ctate-call-for-code-2020/hosts --vault-id @prompt)"

computate "echo 'I deployed an Apache Zookeeper cluster manager config map, volume claim, image stream, service, and deployment config to OpenShift using ansible to manage scaling applications. ';\
(cd /usr/local/src/computate/ansible && ansible-playbook computate_zookeeper_openshift.yml -i inventories/ctate-call-for-code-2020/hosts --vault-id @prompt)"

computate "echo 'I deployed an Apache Solr search engine volume claim, image stream, service, and deployment config to OpenShift using ansible to provide powerful querying, faceting, filtering and sorting of the data. ';\
(cd /usr/local/src/computate/ansible && ansible-playbook computate_solr_openshift.yml -i inventories/ctate-call-for-code-2020/hosts --vault-id @prompt)"

computate "gio open 'https://console.pro-us-east-1.openshift.com/console/project/computateorg/overview';\
echo 'And a Red Hat Single Sign On server which I already had deployed here in another OpenShift environment to manage OpenID Connect user authorization and authentication. '"

computate "gio open 'https://github.com/computate/computate-medicale/';\
echo 'I created a github project called computate-medicale to build a Vert.X application to solve the problem. '"

computate "gio open 'https://github.com/computate/computate-medicale/blob/master/src/main/java/org/computate/medicale/enUS/clinic/MedicalClinic.java';\
echo 'I created code to manage medical clinics. '"

computate "gio open 'https://github.com/computate/computate-medicale/blob/master/src/main/java/org/computate/medicale/enUS/patient/MedicalPatient.java';\
echo 'And medical patients. '"

computate "gio open 'https://github.com/computate/computate-medicale/blob/master/src/main/java/org/computate/medicale/enUS/enrollment/MedicalEnrollment.java';\
echo 'And medical enrollments. '"

computate "gio open 'https://github.com/computate/computate-medicale/blob/master/src/main/java/org/computate/medicale/enUS/user/SiteUser.java';\
echo 'And site users for customizable site options for each registered Red Hat SSO user. '"

computate "gio open 'https://github.com/computate/computate-medicale/blob/master/src/main/java/org/computate/medicale/enUS/design/PageDesign.java';\
echo 'And page designs, for designing custom data driven pages, forms, reports and PDFs in the site. '"

computate "gio open 'https://github.com/computate/computate-medicale/blob/master/src/main/java/org/computate/medicale/enUS/vertx/AppVertx.java#L525';\
echo 'And the rest of the code to bring it all together in a ready to deploy reactive Vert.X application. '"

computate "gio open 'https://github.com/computate/computate-medicale/blob/master/src/main/java/org/computate/medicale/frFR/vertx/AppliVertx.java#L846';\
echo 'You may also note that the code was actually written entirely in French first, and translated into English as a second language, to ensure that it works in multiple languages and supports complete internationalization. '"

computate "gio open 'http://editor.swagger.io/';\
echo 'The application runs based on an OpenApi3 specification for all the web page and API calls in the whole site. You can see the rich collection of APIs available for enrollments, clinics, html, users, pages, patients and more. '"

computate "echo 'I deployed the computate-medicale secret, keystore, image stream, build config, service, deployment config and route to OpenShift using ansible. ';\
gio open 'https://console.rh-us-east-1.openshift.com/console/project/medical/overview';\
(cd /usr/local/src/computate/ansible && ansible-playbook computate_medicale_openshift_enUS.yml -i inventories/ctate-call-for-code-2020/hosts --vault-id @prompt)"

computate "gio open 'https://medical.heytate.com/user';\
echo 'Now lets visit the site and login to the site through the single sign on server. '"

computate "echo 'I created a set of sample patient data for an example clinic and imported that data into my application through its own secured APIs. The backup and restore of data is completely automated using ansible, and the data is always backed up as a password encrypted Ansible vault. Lets watch, as the application data is imported and updated in the UI using websocket updates. We need to sign in through Red Hat SSO to access these parts of the site. ';\
(cd /usr/local/src/computate/ansible && ansible-playbook computate_medicale_restore_enUS.yml -i inventories/ctate-call-for-code-2020/hosts --vault-id @prompt)"

computate "gio open 'https://medical.heytate.com/api/enrollment';\
echo 'Behind every page on the site is a powerful JSON REST API. Here we can see the enrollment data. '"

computate "gio open 'https://medical.heytate.com/api/enrollment?fq=patientFirstName:Sarah&fl=pk,patientFirstName';\
echo 'The API can filter on a first name of Sarah, for example. '"

computate "gio open 'https://medical.heytate.com/api/enrollment?start=10&fl=pk,patientFirstName';\
echo 'paginates... '"

computate "gio open 'https://medical.heytate.com/api/enrollment?rows=100&fl=pk,patientFirstName';\
echo 'returns any number of rows... '"

computate "gio open 'https://medical.heytate.com/api/enrollment?rows=100&fl=patientFirstName';\
echo 'or any number of columns. '"

computate "gio open 'https://medical.heytate.com/page-design';\
echo 'Here we can generate custom, data-driven pages in the site. '"

computate "gio open 'https://medical.heytate.com/page-design/home-page';\
echo 'You can see how the page design is made up of many sortable HTML fragments. '"

computate "gio open 'https://medical.heytate.com';\
echo 'Lets visit the home page to see what a visitor sees. '"

computate "gio open 'https://medical.heytate.com/page/contact-us';\
echo 'Here a visitor can ask the clinic a question. '"

computate "gio open 'https://medical.heytate.com/page/patient-registration-form?var=clinicLocation:Montmartre';\
echo 'Here a visitor can submit a registration form before an appointment. '"

computate "gio open 'https://medical.heytate.com/enrollment';\
echo 'Here a site admin can see the enrollments that have been submitted. '"

computate "gio open 'http://ctate.remote.csb:10383/solr/banana/src/index.html#/dashboard/solr/medical?server=%2Fsolr%2F';\
echo 'Because we use a search engine with our data, we can generate powerful analytics to help us solve COVID-19 related problems. '"

computate "echo 'Here are some dashboards that I built for a clinic to gain insight into their patients most likey affected by COVID-19. '"

computate "gio open 'https://gis.cdc.gov/grasp/COVIDNet/COVID19_5.html';\
echo 'We add relevance to our data based on the graphs I showed earlier from the CDC. '"

computate "echo 'Thanks to the search engine, we can score patients medical conditions, and filter through less relevant conditions like those patients with none: patientMedicalConditions_text_enUS:(*) '"

computate "echo 'If we add a score to certain keywords in a patients medical conditions, we can norrow in on patients most in need of help at this time: patientMedicalConditions_text_enUS:() '"

computate "echo 'We will give asthma a factor of 4: asthma^=4'"

computate "echo 'We will give neurologic conditions a factor of 4: nerves^=4 brain^=4 nervous^=4'"

computate "echo 'We will give respiratory conditions a factor of 4: lung^=4 respiratory^=4 airway^=4'"

computate "echo 'We will give immune conditions a factor of 3: immune^=3'"

computate "echo 'We will give heart conditions a factor of 3: heart^=2'"

computate "echo 'And stomach conditions a factor of 3: stomach^=1 intestine^=1'"

computate "echo 'Now we see how the analytics have changed to focus in on COVID-19 targeted conditions, and here is the list of patients of most concern during this time sorted by score. Like those with combined asthma, airway and lung concerns. '"

computate "echo 'Like that, any medical clinic can extend this open source software to solve their own challenges with data analytics during the pandemic. '"

computate "gio open 'https://github.com/computate/computate-medicale';\
echo 'Check out computate-medicale on github. Don't give up on your dreams, you can do hard things. Courage! '"
