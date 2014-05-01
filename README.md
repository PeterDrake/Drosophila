Drosophila
==========

Fruit fly image analyzer for Software Development, Spring 2014

To use:
1. Run application.
2. To Load in Image or Movie, look under the File Menu. There, you can decide whether you want to load an Image or a Movie, which will open a file selector that will accept the listed file type.
3. When Selecting Images, only .bmp, .jpegs, .png, .gif are usable in this program. When selecting Movies, only .mov files are useable with this program.
For Images:
	The Image should load quickly. To load more images, simply  hit load image again to choose the next image to load.
4. Under the draw tab, click the top imaged of a fly to draw the places where flies are detected on the image.
5. adjust the contrast, pixel threshold, and contrast threshold so that the flies you want are being detected
6. On the image, click and drag to create a box containing flies that you wish to analyze as part of a group. 
Setting multiple boxes to the same label will label all those flies with that label. Flies are only labeled with the latest set label.
Hit the Clear all Groups button to get rid of all the groups.
7. Under Areas, input a number to label the flies in the group with, then click the set Area of Interest button to set those flies to that numbered group.
For Videos:
	When You are satisfied with the detection of flies and your regions of interest, click Analyze video under the File Menu.
	You can also change the sampling rate by changing the number in the sampling rate box
	A popup will appear to say that the video being Analyzed. When it disappears, the Data will be ready.
	Information and Graphs will be displayed in the Tabbed panel and the Graph Panel.
7. You can change the labels on the Graph by clicking "edit the Graph axes' in the edit menu.
8. To save the information from the displays, click "save Graph to file"  to save the graph as a .png or "save Chart data to File" to save the chart data as a comma separated list. Both are under the File menu.
9. To view the flies trajectories, enter numbers of the first frame and the last frame in the appropriate text boxes. Then, under, draw, click the icon of the fly with the trail behind it. It's the second one.
10. To start the process over, click clear all images to clear all images and video from the program. Data will remain until a new video is loaded, as will Groups.

Developer Notes:
-The fly object is the basic model of a fly. It holds its x and y positions in various frames, and holds ways to calculate individual data.
-The model is generally held inside the Analyzer class and the various classes that are connected to it.
-The Algorithm for figuring out where the next fly is is not ideal. A good project would be to improve flydentify, which detects flies and finds out where their paths it, to be more accurate.
--Needs more developer commentary to explain what the hell is going on in flydentify.
-The gui is launched in the GUI class, which holds an Analyzer Panel class which is launched as a frame from the Gui class. It also holds delegate methods for the analyzer panel to communicate with the model.
-Analyzer panel holds all the panels that will be displayed on the screen, and holds ways for the individual displays to interface with the model
--Image Panel holds the image drawing methods, as well as methods to draw boxes to decide which Area's are areas of interest.
--Button Toolbar is where all the user interface is. It holds all the buttons for everything from laoding images to analysis to Setting arena's of interest.
--DataTabbs holds all the data panel chart displays in a tabbed surroundings. it is way bigger than the data panel and I don't know why.
---DataPanel displays the individual fly information. However, there is no way at the moment to know exactly which fly is which.
===GroupPanel is pretty useless, and allows you to see which individual flies and are in which groups. It is extension of DataPanel, so some methods are inherited from DataPanel.
===ArenaPanel is in serious need of a refactor, and allows you to see the average velocity of a group. It is extension of DataPanel, so some methods are inherited from DataPanel.
--GraphPanel draws the graph. Somebody who know how it works should probably describe it better.