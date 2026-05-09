public class Utilizator {
    private int id;
    private String nume;
    private String email;
    private String parola;


    Utilizator(int id,String nume,String email,String parola){
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
    }

    public boolean autentificare(){
        return false;
    }

    public String getEmail(){
        return this.email;
    }

    public String getNume(){
        return this.nume;
    }



}
