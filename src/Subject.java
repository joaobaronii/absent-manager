import java.io.*;

public class Subject {
    private String name;
    private int workload;
    private int absents;
    private int absentsLimit;

    public Subject(String name) {
        this.name = name;
        this.workload = pythonScriptGetWl(name);
        this.absents = 0;
        absentsLimit = (int) Math.ceil(workload * 0.28);
    }

    public Subject(String name, int workload, int absents, int absentsLimit) {
        this.name = name;
        this.workload = workload;
        this.absents = absents;
        this.absentsLimit = absentsLimit;
    }

    public int pythonScriptGetWl(String name) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python", "src/lib/wlextractor.py", name
            );
            Process p = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linha = reader.readLine();

            p.waitFor();

            return Integer.parseInt(linha);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void missedClass() {
        absents++;
    }

    public void excusedClass() {
        if (absents > 0)
            absents--;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    public int getAbsents() {
        return absents;
    }

    public void setAbsents(int absents) {
        this.absents = absents;
    }

    public int getAbsentsLimit() {
        return absentsLimit;
    }

    public void setAbsentsLimit(int absentsLimit) {
        this.absentsLimit = absentsLimit;
    }


    @Override
    public String toString() {
        return name + " - " + absents + "/" + absentsLimit + ", Workload: " + workload;
    }
}
