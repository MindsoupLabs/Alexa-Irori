# ![Irori](https://github.com/MindsoupLabs/Alexa-Irori/raw/master/src/main/resources/alexa/logo/logo-small.png) Irori For Pathfinder
The alexa skill Irori For Pathfinder. If you need to know the stat of a monster, item or spell, just ask Irori!

**This project is currently in beta testing. If you'd like to join our beta test, send me an email at *irori [at] mindsoup.net*.**

## How to use
This project is used in four Alexa skills, all of which are currently in beta:
* Irori
* Spell Finder
* Item Finder
* Monster Finder

Spell Finder, Item Finder and Monster Finder each allow you to find stats only on their particular domain (spells, items or monsters), while Irori combines all of these and allows you to find stats on spells, monsters and items alike. Object name recognition may be more accurate when using the domain specific skills.

To use these skills, simply ask Alexa: "Alexa, ask [skill name] for the [attribute] of [object]". 

### Examples:
* "Alexa, ask Irori for the area of Burning Hands"
* "Alexa, ask Irori what is the cost of a dagger"
* "Alexa, ask Irori what the armor class of a goblin is"
* "Alexa, ask Spell Finder for the range of Magic Missile"
* "Alexa, ask Monster Finder for the hit points of a mummy"
* "Alexa, ask Item Finder to describe a quarterstaff of entwined serpents" 

[![Video Screenshot](https://img.youtube.com/vi/7bWyS5cp5tI/0.jpg)](https://www.youtube.com/watch?v=7bWyS5cp5tI)

## How to setup this project for development
I open sourced this project for a reason, and you're welcome to help out with development!

### Required software
This project uses the following:

* Java 1.8
* Maven
* Postgresql
* IntelliJ IDEA (technically you could use another IDEA, but then you're on your own)

Please ensure you have these installed and configured before proceeding with the next step.

### Setup
1. Clone this project to your local machine, but do not open it yet in IntelliJ
1. Setup Postgresql
   1. Create a database user (for example 'irori_user')
   1. Create a database called 'irori', owned by the user you made
   1. Create a database schema called 'irori' in the 'irori' database, owned by the user you made
1. Open *pom-parent.xml* and follow the commented instructions at the top to create a pom.xml file that contains your local database user information
1. Open  *src/main/resources/application.properties*, create a file called *application-local.properties*. In that file, define the flyway and spring database user (the user you created in step 2.i)
1. Open the project in IntelliJ (via your pom.xml), you should be able to clean and compile it through Maven without problems. This will automatically create and fill the Irori tables in your postgresql schema.
1. Create a Run Configuration in IntelliJ of type Maven with the command line *spring-boot:run*. You should be able to run this without problems. 
1. Open your browser and navigate to http://localhost:8088/swagger-ui.html. This should open the project's Swagger page for you. You're now up and running!

## Legal stuff
Hand icon in the logo by sasha willins from the Noun Project, used under the Creative Commons license.

Pathfinder content used under the Open Game License and the Community Use Policy.

### Community Use Policy
This alexa skill uses trademarks and/or copyrights owned by Paizo Inc., which are used under Paizo's Community Use Policy. We are expressly prohibited from charging you to use or access this content. This alexa skill is not published, endorsed, or specifically approved by Paizo Inc. For more information about Paizo's Community Use Policy, please visit paizo.com/communityuse. For more information about Paizo Inc. and Paizo products, please visit paizo.com.

### OPEN GAME LICENSE Version 1.0a
The following text is the property of Wizards of the Coast, Inc. and is Copyright 2000 Wizards of the Coast, Inc ("Wizards"). All Rights Reserved.

1. Definitions: (a)"Contributors" means the copyright and/or trademark owners who have contributed Open Game Content; (b)"Derivative Material" means copyrighted material including derivative works and translations (including into other computer languages), potation, modification, correction, addition, extension, upgrade, improvement, compilation, abridgment or other form in which an existing work may be recast, transformed or adapted; (c) "Distribute" means to reproduce, license, rent, lease, sell, broadcast, publicly display, transmit or otherwise distribute; (d)"Open Game Content" means the game mechanic and includes the methods, procedures, processes and routines to the extent such content does not embody the Product Identity and is an enhancement over the prior art and any additional content clearly identified as Open Game Content by the Contributor, and means any work covered by this License, including translations and derivative works under copyright law, but specifically excludes Product Identity. (e) "Product Identity" means product and product line names, logos and identifying marks including trade dress; artifacts; creatures characters; stories, storylines, plots, thematic elements, dialogue, incidents, language, artwork, symbols, designs, depictions, likenesses, formats, poses, concepts, themes and graphic, photographic and other visual or audio representations; names and descriptions of characters, spells, enchantments, personalities, teams, personas, likenesses and special abilities; places, locations, environments, creatures, equipment, magical or supernatural abilities or effects, logos, symbols, or graphic designs; and any other trademark or registered trademark clearly identified as Product identity by the owner of the Product Identity, and which specifically excludes the Open Game Content; (f) "Trademark" means the logos, names, mark, sign, motto, designs that are used by a Contributor to identify itself or its products or the associated products contributed to the Open Game License by the Contributor (g) "Use", "Used" or "Using" means to use, Distribute, copy, edit, format, modify, translate and otherwise create Derivative Material of Open Game Content. (h) "You" or "Your" means the licensee in terms of this agreement.

2. The License: This License applies to any Open Game Content that contains a notice indicating that the Open Game Content may only be Used under and in terms of this License. You must affix such a notice to any Open Game Content that you Use. No terms may be added to or subtracted from this License except as described by the License itself. No other terms or conditions may be applied to any Open Game Content distributed using this License.

3. Offer and Acceptance: By Using the Open Game Content You indicate Your acceptance of the terms of this License.

4. Grant and Consideration: In consideration for agreeing to use this License, the Contributors grant You a perpetual, worldwide, royalty-free, non-exclusive license with the exact terms of this License to Use, the Open Game Content.

5. Representation of Authority to Contribute: If You are contributing original material as Open Game Content, You represent that Your Contributions are Your original creation and/or You have sufficient rights to grant the rights conveyed by this License.

6. Notice of License Copyright: You must update the COPYRIGHT NOTICE portion of this License to include the exact text of the COPYRIGHT NOTICE of any Open Game Content You are copying, modifying or distributing, and You must add the title, the copyright date, and the copyright holder's name to the COPYRIGHT NOTICE of any original Open Game Content you Distribute.

7. Use of Product Identity: You agree not to Use any Product Identity, including as an indication as to compatibility, except as expressly licensed in another, independent Agreement with the owner of each element of that Product Identity. You agree not to indicate compatibility or co-adaptability with any Trademark or Registered Trademark in conjunction with a work containing Open Game Content except as expressly licensed in another, independent Agreement with the owner of such Trademark or Registered Trademark. The use of any Product Identity in Open Game Content does not constitute a challenge to the ownership of that Product Identity. The owner of any Product Identity used in Open Game Content shall retain all rights, title and interest in and to that Product Identity.

8. Identification: If you distribute Open Game Content You must clearly indicate which portions of the work that you are distributing are Open Game Content.

9. Updating the License: Wizards or its designated Agents may publish updated versions of this License. You may use any authorized version of this License to copy, modify and distribute any Open Game Content originally distributed under any version of this License.

10. Copy of this License: You MUST include a copy of this License with every copy of the Open Game Content You Distribute.

11. Use of Contributor Credits: You may not market or advertise the Open Game Content using the name of any Contributor unless You have written permission from the Contributor to do so.

12. Inability to Comply: If it is impossible for You to comply with any of the terms of this License with respect to some or all of the Open Game Content due to statute, judicial order, or governmental regulation then You may not Use any Open Game Material so affected.

13. Termination: This License will terminate automatically if You fail to comply with all terms herein and fail to cure such breach within 30 days of becoming aware of the breach. All sublicenses shall survive the termination of this License.

14. Reformation: If any provision of this License is held to be unenforceable, such provision shall be reformed only to the extent necessary to make it enforceable.

15. COPYRIGHT NOTICE
Open Game License v 1.0 Copyright 2000, Wizards of the Coast, Inc.

