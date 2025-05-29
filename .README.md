<a id="readme-top"></a>

<br />

<div align="center">
<h3 align="center">Innovative eKYC</h3>
  <p align="center">
    <br />
    <a href="https://api2-ekycportal.innov8tif.com"><strong>Explore the docs »</strong></a>
    <br />
    <br />
  </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>

## About The Project

A sample backend project to test Innovative eKYC.

#### APIs:

- [Create JourneyId](https://api2-ekycportal.innov8tif.com/emas-ekyc-portal/create-journeyid)
- [Centralized OkayId](https://api2-ekycportal.innov8tif.com/emas-ekyc-portal/centralized-okayid)
- [Centralized OkayDoc](https://api2-ekycportal.innov8tif.com/emas-ekyc-portal/centralized-okaydoc)
- [Centralized OkayFace](https://api2-ekycportal.innov8tif.com/emas-ekyc-portal/centralized-okayface/version-1)
- [Centralized OkayLive](https://api2-ekycportal.innov8tif.com/emas-ekyc-portal/centralized-okaylive)
- [Manual Complete Journey](https://api2-ekycportal.innov8tif.com/emas-ekyc-portal/manual-verification/complete-journey-api)
- [Get Scorecard](https://api2-ekycportal.innov8tif.com/emas-ekyc-portal/scorecard/get-scorecard-result)

### Built With

- [![Java][Java21]][Java-url]
- [![Springboot][Springboot]][Springboot-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Getting Started

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/wanafiq/innovative-ekyc.git
   ```
2. Configure API configs in `application-dev.yaml`
   ```yaml
   application:
      innovative:
         url:
         username:
         password:
         okid-api-key:
         okdoc-api-key:
         okface-api-key:
         oklive-api-key:
   ```
4. Sample images. Place in `src/main/resources/static/` folder. Required images:
   - `front_id.jpg`
   - `back_id.jpg`
   - `selfie.jpg`
   - `passport.jpg`

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## H2 Database
To access h2 db console, navigate to `http://localhost:8080/h2-console`

Database Confgs:
```yaml
 url: jdbc:h2:mem:ekyc;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
 username: root
 password: root
```

## Usage

1. Start the application
   ```sh
   mvn
   ```

2. Build the application
   ```sh
   mvn clean install
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[Springboot]: https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white
[Springboot-url]: https://spring.io/projects/spring-boot
[Java21]: https://img.shields.io/badge/Java-21%2B-orange
[Java-url]: https://www.oracle.com/java/technologies/downloads/?er=221886
