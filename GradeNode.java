/*
   PROGRAM: GradeNode.java
   Written by Meijie Shih
   This program helps Main class to build up a linked list.
*/

public class GradeNode {
    // variables
    private String id;
    private String grade;
    private String letter;
    private GradeNode nextNodeRef;

    /**
     * The constructor.
     */
    public GradeNode(){
        id = "";
        grade = "";
        letter = "";
        nextNodeRef = null;
    }

    /**
     * The constructor accepts 3 strings.
     */
    public GradeNode(String i, String g, String l){
        this.id = i;
        this.grade = g;
        this.letter = l;
        this.nextNodeRef = null;
    }

    /**
     * The constructor accepts 3 strings and 1 GradeNode.
     */
    public GradeNode(String i, String g, String l, GradeNode nextLoc){
        this.id = i;
        this.grade = g;
        this.letter = l;
        this.nextNodeRef = nextLoc;
    }

    /**
     * The insertAfter method.
     */
    public void insertAfter(GradeNode nodeLoc) {
        GradeNode tempNext;
        tempNext = this.nextNodeRef;
        this.nextNodeRef = nodeLoc;
        nodeLoc.nextNodeRef = tempNext;
    }

    /**
     * The getNext method gets location pointed by nextNodeRef.
     */
    public GradeNode getNext(){
        return this.nextNodeRef;
    }

    /**
     * The printGradeInfo returns a string with id and average if the letter grade is the same as selected letter grade,
     * returns null otherwise.
     */
    public String printGradeInfo(String c) {
        String line;
        if (this.letter.equals(c)){
            line = this.id + "\t" + this.grade;
        }
        else{
            line = null;
        }
        return line;
    }
}
