import libjevois as jevois
import cv2
import numpy as np

## Simple example of image processing using OpenCV in Python on JeVois
#
# This module is here for you to experiment with Python OpenCV on JeVois.
#
# By default, we get the next video frame from the camera as an OpenCV BGR (color) image named 'inimg'.
# We then apply some image processing to it to create an output BGR image named 'outimg'.
# We finally add some text drawings to outimg and send it to host over USB.
#
# See http://jevois.org/tutorials for tutorials on getting started with programming JeVois in Python without having
# to install any development software on your host computer.
#
# @author Laurent Itti
# 
# @videomapping YUYV 352 288 30.0 YUYV 352 288 30.0 JeVois PythonSandbox
# @email itti\@usc.edu
# @address University of Southern California, HNB-07A, 3641 Watt Way, Los Angeles, CA 90089-2520, USA
# @copyright Copyright (C) 2017 by Laurent Itti, iLab and the University of Southern California
# @mainurl http://jevois.org
# @supporturl http://jevois.org/doc
# @otherurl http://iLab.usc.edu
# @license GPL v3
# @distribution Unrestricted
# @restrictions None
# @ingroup modules
class PythonSandbox:
    # ###################################################################################################
    ## Constructor
    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("sandbox", 100, jevois.LOG_INFO)
        
    # SPECIAL REPLACED BLUR CONSTANT
        self.__blur_type = 0

	#################################################################################################
        # BEGIN GRIP CONSTANTS
    #################################################################################################


    #################################################################################################
        # END GRIP CONSTANTS
    ##################################################################################################


    ## Process function with USB output
    def process(self, inframe, outframe):
        inimg = inframe.getCvBGR()
        self.timer.start()

    #################################################################################################
        # BEGIN GRIP PROCESSING FUNCTIONS
    ##################################################################################################
    

  	#################################################################################################
        # END GRIP PROCESSING FUNCTIONS
    ##################################################################################################	

        outimg = cv2.Laplacian(inimg, -1, ksize=5, scale=0.25, delta=127)
        cv2.putText(outimg, "JeVois Python Sandbox", (3, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255),
                    1, cv2.LINE_AA)
        fps = self.timer.stop()
        height, width, channels = outimg.shape
        cv2.putText(outimg, fps, (3, height - 6), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255), 1, cv2.LINE_AA)
        outframe.sendCvBGR(outimg)


#################################################################################################
   # BEGIN GRIP METHODS (@StaticMethod stuff)
##################################################################################################
    

#################################################################################################
	# END GRIP CODE
##################################################################################################