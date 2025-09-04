# halo-plugin-sitepush

Halo 2.x Active Search Engine Push Plugin

## Feature Overview
This plugin can push links from article modules and page modules within the site to major search engine indexing platforms.

### Feature Highlights
- Supports verification of site indexing platforms
- Supports pushing site links to major search engine indexing platforms

### Currently Supported Indexing Platforms:
- [x] [Baidu Indexing](https://ziyuan.baidu.com)
- [x] [Bing Indexing](https://www.bing.com/webmasters)
- [x] [Google Indexing](https://search.google.com/search-console/)
- [ ] More (PRs welcome)

## Installation Method
- Download the latest JAR file from the [release page](https://github.com/Stonewuu/halo-plugin-sitepush/releases).
- Upload and install the JAR file in the Halo backend plugin management.

## Usage Instructions
- After installation, configure in the Halo backend `Plugins` -> `Site Push Plugin` configuration page.
- Push timing: When the plugin starts, when pages and articles are published
- Google push requires network access to Google, same for other pushes

![Plugin Screenshot](https://github.com/Erzbir/halo-plugin-sitepush/assets/100007608/0f258f18-1e2d-4d6d-b7ca-7c8aee8ffc9f)

## Configuration Notes
- If already authenticated through DNS records or other methods, website verification code is not needed
- This plugin's push supports proxy (supports no authentication and basic auth), after configuring this, ensure the proxy is available

## Contributing to Development

Detailed plugin development documentation: <https://docs.halo.run/developer-guide/plugin/hello-world>

```bash
git clone https://github.com/Stonewuu/halo-plugin-sitepush.git

# Or after you fork

git clone https://github.com/{your_github_id}/halo-plugin-sitepush.git
```

```bash
cd path/to/halo-plugin-sitepush
```

```bash
# macOS / Linux
./gradlew build

# Windows
./gradlew.bat build
```

Modify Halo configuration file:

```yaml
halo:
  plugin:
    runtime-mode: development
    fixedPluginPath:
      - "/path/to/halo-plugin-sitepush"
