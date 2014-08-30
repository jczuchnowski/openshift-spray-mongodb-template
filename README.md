openshift-spray-mongodb-template
================================

Template for Spray/ReactiveMongo based application deployable to OpenShift PaaS.

### Prerequisites:

1. Creating an OpenShift account.
2. Install OpenShift Client Tools.

### Prepare your gear:

~~~ bash
rhc app create example-app jbossews-2.0
~~~

~~~ bash
rhc cartridge add mongodb-2.4 -a example-app
~~~

~~~ bash
rhc cartridge add rockmongo-1.1 -a example-app
~~~