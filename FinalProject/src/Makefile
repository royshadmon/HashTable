JAVASRC = Main.java UIMS.java SLItemList.java SItem.java Conversion.java
MAINCLASS = Main
CLASSES = ${JAVASRC:.java=.class}
JARCLASSES = ${CLASSES}
JARFILE = UIMS

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest 
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest 
	chmod +x $(JARFILE)
        
$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)
      
clean:
	rm $(CLASSES) $(JARFILE)
