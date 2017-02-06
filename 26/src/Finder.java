/**
 */
class Finder {
    private int start;
    private int end = 0;
    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }


    private String whereToFind;
    private String startString;
    private String endString;

    public Finder(String whereToFind, String startString, String endString) {
        this.whereToFind = whereToFind;
        this.startString = startString;
        this.endString = endString;
        end=0;
        start=0;
    }

    public boolean hasNext() {
        if ((whereToFind.indexOf(startString, end) == -1) || (whereToFind.indexOf(endString, start) == -1))
            return false;
        else return true;
    }

    public String findSubStringBetween() {

        while (true) {

            start = whereToFind.indexOf(startString, end);
            if (start == -1) break;
            if (startString.equals(endString)&&(start<whereToFind.length())){
                end = whereToFind.indexOf(endString, start+1);
            }else {
                end = whereToFind.indexOf(endString, start);
            }
            if (end!=-1)return whereToFind.substring(start, end+endString.length());

        }
        return null;
    }

    public String findSubStringBetweenWithoutTags(){
        String result;
        result=findSubStringBetween();
        result=result.substring(startString.length(),result.length()-endString.length());
        return result;
    }
}
