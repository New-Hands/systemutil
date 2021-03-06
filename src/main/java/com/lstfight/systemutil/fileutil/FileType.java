package com.lstfight.systemutil.fileutil;

public enum FileType {
    /**
     *
     */
    picture("pictureFilePreviewImpl"),
    /**
     *
     */
    compress("compressFilePreviewImpl"),
    /**
     *
     */
    office("officeFilePreviewImpl"),
    /**
     *
     */
    simText("simTextFilePreviewImpl"),
    /**
     *
     */
    pdf("pdfFilePreviewImpl"),
    /**
     *
     */
    other("otherFilePreviewImpl"),
    /**
     *
     */
    media("mediaFilePreviewImpl");

    private String instanceName;
    FileType(String instanceName){
        this.instanceName=instanceName;
    }

     public String getInstanceName() {
        return instanceName;
    }


}
