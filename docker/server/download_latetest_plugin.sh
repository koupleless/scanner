tmp_metadata=$(mktemp)
curl -o $tmp_metadata https://oss.sonatype.org/content/repositories/snapshots/com/alipay/sofa/koupleless/koupleless-java-sonar-plugin/LATEST-SNAPSHOT/maven-metadata.xml
timestamp=`cat $tmp_metadata | grep "<timestamp>" | sed -e 's/.*<timestamp>\(.*\)<\/timestamp>.*/\1/'`
build_number=`cat $tmp_metadata | grep "<buildNumber>" | sed -e 's/.*<buildNumber>\(.*\)<\/buildNumber>.*/\1/'`

plugin_name="koupleless-java-sonar-plugin-LATEST-$timestamp-$build_number.jar"

sudo wget -O $SONARQUBE_HOME/lib/extensions/koupleless-java-sonar-plugin-LATEST-SNAPSHOT.jar https://oss.sonatype.org/service/local/repositories/snapshots/content/com/alipay/sofa/koupleless/koupleless-java-sonar-plugin/LATEST-SNAPSHOT/$plugin_name