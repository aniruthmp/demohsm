FROM amazonlinux:1

RUN yum install -y java-1.8.0-openjdk && \
yum install -y wget && \
yum install -y procps && \
yum clean all

#install cloudHSM client
RUN wget https://s3.amazonaws.com/cloudhsmv2-software/CloudHsmClient/EL6/cloudhsm-client-latest.el6.x86_64.rpm
RUN yum install -y ./cloudhsm-client-latest.el6.x86_64.rpm
ADD customerCA.crt /opt/cloudhsm/etc/customerCA.crt

RUN wget https://s3.amazonaws.com/cloudhsmv2-software/CloudHsmClient/EL6/cloudhsm-client-jce-latest.el6.x86_64.rpm
RUN yum install -y ./cloudhsm-client-jce-latest.el6.x86_64.rpm

RUN /opt/cloudhsm/bin/configure -a 172.31.80.88

ADD start.sh start.sh
RUN chmod 755 start.sh

VOLUME /tmp
COPY target/*.jar app.jar
ENV SERVER_PORT 8080
ENV LD_LIBRARY_PATH /opt/cloudhsm/lib
EXPOSE ${SERVER_PORT}
CMD ["./start.sh"]