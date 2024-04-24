FROM sonarqube:10.4.1-community

COPY ./download_latetest_plugin.sh /opt/sonarqube/docker/download_latest_plugin.sh
COPY ./customized_entrypoint.sh /opt/sonarqube/docker/customized_entrypoint.sh

USER root
RUN apt-get update && apt-get install -y sudo
RUN echo 'sonarqube ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers

USER sonarqube

ENTRYPOINT ["/opt/sonarqube/docker/customized_entrypoint.sh"]
