import libjevois as jevois
import cv2
import numpy as np


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