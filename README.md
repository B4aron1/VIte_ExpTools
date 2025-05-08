<!-- VITE-VULN-SCANNER -->
<div align="center">
  <img src="https://img.shields.io/badge/VITE-漏洞检测工具-00ccff?style=for-the-badge&logo=v&logoColor=white&labelColor=0a0e14">
  <br>
  <img src="https://img.shields.io/badge/JDK-8+-1eff8e?style=flat-square&logo=java&logoColor=white&labelColor=1e3c5a">
  <img src="https://img.shields.io/badge/平台-跨平台-ac8eff?style=flat-square&logo=windows&logoColor=white&labelColor=2d1e5a">
  <img src="https://img.shields.io/badge/授权-仅限合法使用-ff5252?style=flat-square&logo=shield&logoColor=white&labelColor=7a0000">
</div>

# VITE 任意文件读取漏洞检测工具

> `[ 高级黑客工具 ]` 用于检测和利用Vite开发服务器中存在的任意文件读取漏洞

这是一款专业的漏洞检测工具，采用JavaFX开发的GUI应用程序，可以快速识别Vite开发服务器中可能存在的文件读取漏洞，并提供交互式操作界面。工具使用黑客风格的界面设计，同时保持了高效易用的特性。

![image-20250508172151873](https://b4aron1picture.oss-cn-shanghai.aliyuncs.com/PIcture/image-20250508172151873.png)


## ⚡ 主要功能

```bash
# 工具提供以下核心功能
├── 单个URL检测 - 针对特定目标进行精准漏洞扫描
├── 批量检测模式 - 通过文本文件批量导入目标
├── 操作系统选择 - 支持Windows/Linux系统漏洞利用向量
└── FUZZ测试 - 使用内置字典对目标进行模糊测试
```

## 🔧 环境要求

- **JDK**: 8 或更高版本
- **权限**: 仅限授权范围内的测试目标

## 🚀 安装和使用

### 运行工具

```bash
# 直接运行JAR文件
java -jar target/demo2-1.0-SNAPSHOT.jar
```

## 📖 使用指南

### 单个目标检测

1. 在"TARGET_URL"输入框中输入目标Vite服务器URL
2. 选择目标系统类型（Windows/Linux）
3. 点击"开始检测"按钮
4. 查看输出终端中的检测结果

### 批量目标检测

1. 准备一个包含多个目标URL的文本文件（每行一个）
2. 点击"选择文件"按钮上传目标文件
3. 选择目标系统类型（Windows/Linux）
4. 点击"开始检测"按钮进行批量扫描
5. 查看输出终端中的检测结果

### FUZZ检测模式

1. 在"TARGET_URL"输入框中输入基础URL
2. 点击"FUZZ检测"按钮
3. 系统将使用内置FUZZ字典尝试读取多种敏感文件
4. 查看输出终端中的检测结果

## ⚠️ 免责声明

```
[ WARNING ]
本工具仅用于授权的安全测试和漏洞研究目的。
未经授权使用本工具对目标系统进行测试可能违反相关法律法规。
使用本工具导致的任何法律责任由使用者自行承担。
```

## 🔒 技术原理

Vite开发服务器在某些版本中存在一个文件路径穿越漏洞，允许攻击者通过特定的URL路径（如`/@fs/`路径）读取服务器上的任意文件。此工具通过构造特定的HTTP请求来检测此类漏洞是否存在，并评估其可利用性。

## 💼 开发者信息

- **作者**: Baron
- **版本**: 1.0
- **协议**: 仅供授权使用 