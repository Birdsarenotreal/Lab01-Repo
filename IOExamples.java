package map.curs6.io;



import java.util.*;
import java.io.*;


public class IOExamples {
    static List<Student> citesteStudenti(String numefis){
        List<Student> ls=new ArrayList<Student>();

        try(BufferedReader br=new BufferedReader(new FileReader(numefis))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] elems=linie.split("[|]");
                if (elems.length<2){
                    System.err.println("Linie invalida "+linie);
                    continue;
                }
                Student stud=new Student(elems[0], Double.parseDouble(elems[1]));
                ls.add(stud);

            }
        } catch (IOException e) {
            System.err.println("Eroare citire "+e);
        }
        return ls;
    }

    static List<Student> citesteStudenti(){
        List<Student> lista=new ArrayList<Student>();

        try(BufferedReader br=new BufferedReader(new InputStreamReader(System.in))){

            System.out.println("Start App. La terminare introduceti cuvantul \"gata\"");
            boolean gata=false;
            while(!gata){
                System.out.println("Introduceti numele: ");
                String snume=br.readLine();
                if ("gata".equalsIgnoreCase(snume)){
                    gata=true;
                    continue;
                }
                System.out.println("Introduceti media: ");
                String smedia=br.readLine();
                if ("gata".equalsIgnoreCase(smedia)){
                    gata=true;
                    continue;
                }
                try{
                    double media=Double.parseDouble(smedia);
                    lista.add(new Student(snume,media));
                }catch(NumberFormatException nfe){
                    System.out.println("Eroare: "+nfe);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citire "+e);
        }
        return lista;
    }
    static void printStudenti(List<Student> studs){
        for(Student s:studs)
            System.out.println(s);
    }

    static void printStudentiBW(List<Student> studs, String numefis){

        try(BufferedWriter bw=new BufferedWriter(new FileWriter(numefis))){
            for(Student stud: studs){
                bw.write(stud.getNume()+'|'+stud.getMedia());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Eroare scriere BW "+e);
        }

    }
    static List<Student> citesteStudentiDataInput(String numefis){
        List<Student> studs=new ArrayList<Student>();
        try(DataInputStream input=new DataInputStream(new FileInputStream(numefis))){
            while(true){
                String nume=input.readUTF();
                double media=input.readDouble();
                studs.add(new Student(nume,media));
            }
        }catch (EOFException e){
            System.out.println("reached end of file ");
        }
        catch (IOException e) {
            System.err.println("Eroare citire DI "+e);
        }
        return studs;
    }
    static void printStudentiDataOutput(List<Student> studs, String numefis){
        try (DataOutputStream output=new DataOutputStream(new FileOutputStream(numefis))){

            for(Student stud: studs){
                output.writeUTF(stud.getNume());
                output.writeDouble(stud.getMedia());
            }
        }  catch (IOException e) {
            System.err.println("Eroare scriere DO "+e);
        }
    }

    static void printStudentiPrintWriter(List<Student> studs, String numefis){
        try(PrintWriter pw=new PrintWriter(numefis)){
            for(Student stud: studs){
                pw.println(stud.getNume()+'|'+stud.getMedia());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Eroare scriere PW "+e);
        }
    }

    static void printStudentiPWTabel(List<Student> studs, String numefis){
        try(PrintWriter pw=new PrintWriter(numefis)){
            String linie=getLinie('-',48);
            int crt=0;
            for(Student stud:studs){
                pw.println(linie);
                //pw.printf("| %3d | %-30s | %5.2f |%n",(++crt),stud.getNume(),stud.getMedia());
                pw.format("| %3d | %-30s | %5.2f |%n",(++crt),stud.getNume(),stud.getMedia());
            }
            if (crt>0)
                pw.println(linie);
        } catch (FileNotFoundException e) {
            System.err.println("Eroare scriere PWTabel "+e);
        }
    }

    private static String getLinie(char c, int length){
        char[] tmp=new char[length];
        Arrays.fill(tmp,c);
        return String.valueOf(tmp);
    }

    static List<Student> citesteStudentiScanner(){
        List<Student> lista=new ArrayList<Student>();
        try(Scanner scanner=new Scanner(System.in)){
            System.out.println("Start App. La terminare introduceti cuvantul \"gata\"");
            boolean gata=false;
            while(!gata){
                System.out.println("Introduceti numele: ");
                String snume=scanner.nextLine();
                if ("gata".equalsIgnoreCase(snume)){
                    gata=true;
                    continue;
                }
                System.out.println("Introduceti media: ");
                if (scanner.hasNextDouble()) {
                    double media=scanner.nextDouble();
                    lista.add(new Student(snume,media));
                    scanner.nextLine();
                    continue;
                }else{
                    String msj=scanner.nextLine();
                    if ("gata".equalsIgnoreCase(msj)){
                        gata=true;
                        continue;
                    }else
                        System.out.println("Trebuie sa introduceti media studentului");
                }
            }
        }
        return lista;
    }

    


    static void printStudentiRAF(List<Student> studs, String numefis){
        try (RandomAccessFile  out=new RandomAccessFile(numefis,"rw")){

            for(Student stud: studs){
                out.writeUTF(stud.getNume());
                out.writeDouble(stud.getMedia());
            }
        }  catch (IOException e) {
            System.err.println("Eroare scriere RAF "+e);
        }
    }
    static List<Student> citesteStudentiRAF(String numefis){
        List<Student> studs=new ArrayList<Student>();
        try(RandomAccessFile in=new RandomAccessFile(numefis, "r")){
            while(true){
                String nume=in.readUTF();
                double media=in.readDouble();
                studs.add(new Student(nume,media));
            }
        }
        catch (IOException e) {
            System.err.println("Eroare la citire "+e);
        }
        return studs;
    }
    static void adaugaStudent(Student stud, String numefis){

        try(RandomAccessFile out=new RandomAccessFile(numefis, "rw")){
            out.seek(out.length());
            out.writeUTF(stud.getNume());
            out.writeDouble(stud.getMedia());
        }  catch (IOException e) {
            System.err.println("Eroare RAF "+e);
        }
    }

    static void printSerializabil(List<Student> studs, String numefis){
        try(ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(numefis))){

            out.writeObject(studs);
        } catch (IOException e) {
            System.err.println("Eroare serializare "+e );
        }
    }
    @SuppressWarnings("unchecked")
    static  List<Student> citesteSerializabil(String numefis){
        List<Student> rez=null;

        try(ObjectInputStream in=new ObjectInputStream(new FileInputStream(numefis))){
            rez=(List<Student>)in.readObject();
        } catch (IOException|ClassNotFoundException e) {
            System.err.println("Eroare deserializare"+e);
        }
        return rez;
    }

    public static void main(String[] args) {
    List<Student> st=citesteStudenti("Studenti.txt");
      // List<Student> st=citesteStudenti();
       // List<Student> st=citesteStudentiDataInput("StudentiBytes202a.txt");
       // List<Student> st= citesteStudentiScanner();
        //List<Student> st=citesteStudentiScanner("Studenti.txt");
     // List<Student> st=citesteStudentiRAF("StudentiBytes.txt");
        Collections.sort(st);
        printStudenti(st);
        //printStudentiBW(st,"StudentiBW.txt");
      // printStudentiDataOutput(st,"StudentiBytes202a.txt");
      //  printStudentiPrintWriter(st, "StudentiPW.txt");
      //  printStudentiPWTabel(st,"StudentiPWTabel.txt");
       printStudentiRAF(st, "StudentiRAF23.txt");

        /*adaugaStudent(new Student("B  C", 5.3), "StudentiBytes.txt");
        System.out.println("Dupa adaugare");
        st=citesteStudentiRAF("StudentiBytes.txt");
        printStudenti(st);       */
       // printSerializabil(st,"Studenti.ser");
        //printStudenti(citesteSerializabil("Studenti.ser"));

    }
}
