package main.java;

public class PDB {
    private  int start;
    private  int end;
    private  String type;
    private  int classPdb;
    private  int topology;
    private  int fold;
    private  int clan;
    private  String classTopology;
    private  String classTopologyFold;
    private  String classTopologyFoldClan;
    private  String pdbId;
    private  String pdbChain;
    private  String repeatsdbId;
    private  String origin;
    private  boolean reviewed;
    private  String[] annotator;
    private  String regionId;
    private  int regionUnitsNum;
    private  double regionAverageUnitLength;

    // Costruttore
    public PDB(int start, int end, String type, int classPdb, int topology, int fold, int clan,
               String classTopology, String classTopologyFold, String classTopologyFoldClan,
               String pdbId, String pdbChain, String repeatsdbId, String origin, boolean reviewed,
               String[] annotator, String regionId, int regionUnitsNum, double regionAverageUnitLength) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.classPdb = classPdb;
        this.topology = topology;
        this.fold = fold;
        this.clan = clan;
        this.classTopology = classTopology;
        this.classTopologyFold = classTopologyFold;
        this.classTopologyFoldClan = classTopologyFoldClan;
        this.pdbId = pdbId;
        this.pdbChain = pdbChain;
        this.repeatsdbId = repeatsdbId;
        this.origin = origin;
        this.reviewed = reviewed;
        this.annotator = annotator;
        this.regionId = regionId;
        this.regionUnitsNum = regionUnitsNum;
        this.regionAverageUnitLength = regionAverageUnitLength;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getClassPdb() {
        return classPdb;
    }

    public void setClassPdb(int classPdb) {
        this.classPdb = classPdb;
    }

    public int getTopology() {
        return topology;
    }

    public void setTopology(int topology) {
        this.topology = topology;
    }

    public int getFold() {
        return fold;
    }

    public void setFold(int fold) {
        this.fold = fold;
    }

    public int getClan() {
        return clan;
    }

    public void setClan(int clan) {
        this.clan = clan;
    }

    public String getClassTopology() {
        return classTopology;
    }

    public void setClassTopology(String classTopology) {
        this.classTopology = classTopology;
    }

    public String getClassTopologyFold() {
        return classTopologyFold;
    }

    public void setClassTopologyFold(String classTopologyFold) {
        this.classTopologyFold = classTopologyFold;
    }

    public String getClassTopologyFoldClan() {
        return classTopologyFoldClan;
    }

    public void setClassTopologyFoldClan(String classTopologyFoldClan) {
        this.classTopologyFoldClan = classTopologyFoldClan;
    }

    public String getPdbId() {
        return pdbId;
    }

    public void setPdbId(String pdbId) {
        this.pdbId = pdbId;
    }

    public String getPdbChain() {
        return pdbChain;
    }

    public void setPdbChain(String pdbChain) {
        this.pdbChain = pdbChain;
    }

    public String getRepeatsdbId() {
        return repeatsdbId;
    }

    public void setRepeatsdbId(String repeatsdbId) {
        this.repeatsdbId = repeatsdbId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String[] getAnnotator() {
        return annotator;
    }

    public void setAnnotator(String[] annotator) {
        this.annotator = annotator;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public int getRegionUnitsNum() {
        return regionUnitsNum;
    }

    public void setRegionUnitsNum(int regionUnitsNum) {
        this.regionUnitsNum = regionUnitsNum;
    }

    public double getRegionAverageUnitLength() {
        return regionAverageUnitLength;
    }

    public void setRegionAverageUnitLength(double regionAverageUnitLength) {
        this.regionAverageUnitLength = regionAverageUnitLength;
    }
}
