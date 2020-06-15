<!-- MAIN TITLE -->
# → LinuxBoi - JDA Rewrite

<!-- LINUX BOI PICTURE -->
  <img align="right" src="https://i.imgur.com/aiIXeCJ.png" width=30%>

<!-- BADGES -->
  ![OpenJDK Versions](https://img.shields.io/badge/OpenJDK-8%20--%2011-orange?style=flat-square)
  ![JDA Version](https://img.shields.io/badge/JDA-4.1.1.165-orange?style=flat-square)
  ![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=flat-square)

<!-- KEY INFORMATION HEADER -->
## ➤ Important Information

  * Rewrite for my [**LinuxBoi**](https://github.com/TrackRunny/LinuxBoi) Python bot in Java
  * Mostly doing this to learn the JDA library
  * Not all commands will be added

<!-- MODULES HEADER -->
### ➤ Modules

  * Information
  * Moderation
  * Utility
  * Fun
  * Meme

<!-- INSTALLATION HEADER -->
### ➤ Setup
To start, make sure you have **OpenJDK 8 - 11** installed on your computer. Then follow the instructions below.

* First, clone the repo and change directories into it.

```markdown
git clone https://github.com/TrackRunny/LinuxBoi-JDA-Rewrite.git
<!-- Cloning the repo -->

cd LinuxBoi-JDA-Rewrite
<!-- Changing directories -->
```

* Next, use gradle to build a jar file of the bot.

```markdown
./gradlew shadowJar
<!-- Use this if you are on Mac OS X or Linux -->

gradlew.bat shadowJar
<!-- Use this if you are on windows -->
```

* Congratulations, you should now have a jar located in `build/libs` directory.

---

Please make sure you **create** a `.env` file in the root directory where the jar file is being ran. It should have these values.

```
TOKEN="YOUR DISCORD BOT TOKEN"
IP_INFO_TOKEN="YOUR IP INFO TOKEN"
KSOFT_TOKEN="YOUR KSOFT TOKEN"
OWNER_ID=YOUR OWNER ID
PREFIX="j!"
```

Everything is now set up. To start the bot you can do `java -jar jarfilename.jar` **or** you may want to create a shell or bat script to do this for you.

<!-- LICENSE INFO -->
## ➤ License

  This project is licensed under the GPLv3.

<!-- END OF README -->
## ➤ Questions / Contact me

  * Discord Account: `TrackRunny#0001`
