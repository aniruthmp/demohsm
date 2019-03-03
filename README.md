## HSM Instructions

1. Create HSM cluster
1. Once cluster is done, update its security-group to public (0.0.0.0) from custom
1. create HSM
1. download all the 4 certificates
1. Sign the CSR

```bash
$ openssl genrsa -aes256 -out customerCA.key 2048

$ openssl req -new -x509 -days 3652 -key customerCA.key -out customerCA.crt

$ openssl x509 -req -days 3652 -in <cluster ID>_ClusterCsr.csr \
                              -CA customerCA.crt \
                              -CAkey customerCA.key \
                              -CAcreateserial \
                              -out <cluster ID>_CustomerHsmCertificate.crt

```

1. Upload the newly created `cluster-noilmsndo4r_CustomerHsmCertificate.crt` file and `customerCA.crt` to initialize the cluster
1. You must set the cluster password using CloudHSM before you can use the Cluster. Refer (https://docs.aws.amazon.com/cloudhsm/latest/userguide/activate-cluster.html)

    - ssh -i "rohit.pem" ec2-user@ec2-3-89-65-5.compute-1.amazonaws.com
    - Refer https://docs.aws.amazon.com/cloudhsm/latest/userguide/install-and-configure-client-linux.html
    - ```scp -i rohit.pem customerCA.crt ec2-user@ec2-3-89-65-5.compute-1.amazonaws.com:/home/ec2-user```
    - sudo /opt/cloudhsm/bin/configure -a 172.31.80.88
    - sudo stop cloudhsm-client
    - sudo start cloudhsm-client
    - Now do https://docs.aws.amazon.com/cloudhsm/latest/userguide/activate-cluster.html
    - loginHSM CO admin pivotal
    - createUser CU pivotal_user pivotal

1. https://docs.aws.amazon.com/cloudhsm/latest/userguide/java-library-install.html
    - Run `info server 0` to get the partiton
1. Check the SpringBoot app now
    -   scp -i ~/Downloads/hsm/rohit.pem ./target/demo-0.0.1-SNAPSHOT.jar ec2-user@ec2-3-89-65-5.compute-1.amazonaws.com:/home/ec2-user

sshuttle -r ec2-user@ec2-3-89-65-5.compute-1.amazonaws.com 172.31.80.88/32 -v