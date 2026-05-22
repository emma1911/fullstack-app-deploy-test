# CI/CD Tools Installation Report

This repository contains the documentation and practical steps for installing and testing two essential tools used in a CI/CD environment:

- SonarQube
- Sonatype Nexus Repository

## Project Objective

The objective of this project is to understand how to install and configure key DevOps tools on the same server in order to improve software quality, artifact management, and deployment reliability.

## Included Documents

| File | Description |
|------|-------------|
| `sonarqube.md` | Complete installation and testing guide for SonarQube |
| `nexus.md` | Complete installation and testing guide for Nexus Repository |
| `images/` | Screenshots used in the documentation |

## Tools Used

### SonarQube
A static code analysis platform used to detect:
- Bugs
- Vulnerabilities
- Code smells
- Maintainability issues

### Nexus Repository
A repository manager used to:
- Store project artifacts (`.jar`, `.war`)
- Manage dependencies
- Host release and snapshot versions

## Environment

### Server VM
for each server his own VM
- Jenkins with the ip@ 192.168.56.21
- SonarQube with ip@ 192.168.56.22
- Nexus Repository with ip@ 192.168.56.23

## Repository Structure

```text
.
├── README.md
├── LAB_SONARQUBE.md
├── LAB_NEXUS.md
└── images/
