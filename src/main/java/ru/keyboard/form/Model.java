package ru.keyboard.form;

import java.nio.file.Path;

/**
 * @author Sokolova
 * @since 23.01.2021
 */
public class Model {

    private Path oldMapFile;

    private String providerName;
    private String keyboardReadableName;
    private String layout;
    
    private boolean isVirtualMap;

    private int rows;
    private int columns;

    private int keyWidthPx;
    private int keyHeightPx;
    private int keyDistancePx;

    private int xStartPx;
    private int yStartPx;

    private String[][] scanCodes;

    public Path getOldMapFile() {
        return oldMapFile;
    }

    public void setOldMapFile(Path oldMapFile) {
        this.oldMapFile = oldMapFile;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getKeyboardReadableName() {
        return keyboardReadableName;
    }

    public void setKeyboardReadableName(String keyboardReadableName) {
        this.keyboardReadableName = keyboardReadableName;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public boolean isVirtualMap() {
        return isVirtualMap;
    }

    public void setIsVirtualMap(boolean virtualMap) {
        isVirtualMap = virtualMap;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getKeyWidthPx() {
        return keyWidthPx;
    }

    public void setKeyWidthPx(int keyWidthPx) {
        this.keyWidthPx = keyWidthPx;
    }

    public int getKeyHeightPx() {
        return keyHeightPx;
    }

    public void setKeyHeightPx(int keyHeightPx) {
        this.keyHeightPx = keyHeightPx;
    }

    public int getKeyDistancePx() {
        return keyDistancePx;
    }

    public void setKeyDistancePx(int keyDistancePx) {
        this.keyDistancePx = keyDistancePx;
    }

    public int getXStartPx() {
        return xStartPx;
    }

    public void setXStartPx(int xStartPx) {
        this.xStartPx = xStartPx;
    }

    public int getYStartPx() {
        return yStartPx;
    }

    public void setYStartPx(int yStartPx) {
        this.yStartPx = yStartPx;
    }

    public String[][] getScanCodes() {
        return scanCodes;
    }

    public void setScanCodes(String[][] scanCodes) {
        this.scanCodes = scanCodes;
    }
}
