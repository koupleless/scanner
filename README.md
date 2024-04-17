# Koupleless 代码扫描工具
Koupleless 是一个多模块部署的框架。
Koupleless 模式下一个 app 是一个代码包，一个代码包会在一个常驻进程中(基座)动态地安装 / 卸载，一个基座可能有 N 个代码包。
这种多模块部署的模式打破了传统的单进程单 app 的假设，因此我们需要考虑单进程和多 app 模式的兼容性。
一个具体的兼容性问题如下：基座中的全局变量被多个 app 覆盖, 导致 app 之间相互污染等, 引入正确性问题。
为了保证 app 的正确性，即确保 koupleless 模式不会引入正确性问题，开发者可能需要对基座的兼容性做仔细的回归验证。
同时，不兼容的代码块也有一些通用的模式是可以被识别的，这能加速开发者的调试和验证过程，提高开发效率。
该仓库提供了一个代码扫描工具，可以帮助开发者识别不兼容的代码块，提高验证效率。

# 架构设计
Koupleless 扫描工具是开源流行的代码扫描工具 SonarQube 的一个插件。
用户可以通过 SonarQube 的 Web 界面 / API 来查看扫描结果。
用户可以通过 SonarQube 的 CLI 来执行扫描任务。

## 部署 SonarQube 服务
### 已有 SonarQube 进程
如果已有 SonarQube 进程，可以直接安装插件，步骤如下：
1. 从 [地址](todo) 下载插件 jar 包
2. 将 jar 包放到 SonarQube 的插件目录下：`$SONARQUBE_HOME/lib/extensions/`
其中，Sonarqube 的服务端版本至少为 todo.

### 没有 SonarQube 进程
可以通过项目的 docker/server 目录下的 Dockerfile 来构建一个 SonarQube 服务。
构建完成后，可以通过 docker/server 的 docker-compose.yml 配置文件来启动服务。
当然，也可以通过其他方式，比如 k8s 来部署服务。

## 使用 SonarQube CLI
建议参照 SonarQube 的[官方文档](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner/#sonarscanner-from-docker-image)的 docker 封装好的 cli 命令来使用 SonarQube CLI。
使用命令如下：
```shell
todo
```

# 扫描规则
- Java: [扫描规则](todo)
