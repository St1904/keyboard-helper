package ru.keyboard.form;

/**
 * @author Sokolova
 * @since 01.02.2021
 */
public class MapTemplater {

    private final StringBuilder sb;

    public MapTemplater() {
        sb = new StringBuilder();
    }

    public MapTemplater startDescription() {
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<keyboard");
        return this;
    }

    public MapTemplater appendName(String name) {
        sb.append(" name='").append(name).append("'");
        return this;
    }

    public MapTemplater appendLayout(String layout) {
        sb.append(" layout='").append(layout).append("'");
        return this;
    }

    public MapTemplater appendModel(String model) {
        sb.append(" model='").append(model).append("'");
        return this;
    }

    public MapTemplater endDescription() {
        sb.append(">\n");
        return this;
    }

    public MapTemplater startButtonsBlock(int rows, int columns, int keyWidth, int keyHeight) {
        sb.append("    <buttons CountX='").append(columns).append("' CountY='").append(rows).append("'>\n")
                .append("        <main width='").append(keyWidth).append("' height='").append(keyHeight).append("' kbdAlphaNumeric='false'>\n");
        return this;
    }

    public MapTemplater addButton(int keyX, int keyY, String scanCode) {
        sb.append("            <button x='").append(keyX).append("' y='").append(keyY).append("' scanCode='").append(scanCode).append("'/>\n");
        return this;
    }

    public MapTemplater addEmptyLine() {
        sb.append("\n");
        return this;
    }

    public MapTemplater addEnd() {
        sb.append("        </main>\n").append("    </buttons>\n").append("</keyboard>");
        return this;
    }

    public String getResult() {
        return sb.toString();
    }
}
