import model.Ganho;
import model.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
void main() {
  Scanner scanner = new Scanner(System.in);
  System.out.println("Qual seu nome?");
  String nome = scanner.nextLine();
  System.out.println("Qual seu email?");
  String email = scanner.nextLine();
  System.out.println("Qual sua senha?");
  String senha = scanner.nextLine();
  Usuario user = new Usuario(nome, email, senha);





  System.out.println("Nome: " + user.getNome());
  System.out.println("Email: " + user.getEmail());
  System.out.println("Senha: " + user.getSenha());

  ArrayList<Ganho> ganhosList = new ArrayList<>();
 double  totalifood = 0;
 double total99 = 0;
 String resposta;
 do {  System.out.println("Diga o ganho do dia");
   double valor = scanner.nextDouble();
   scanner.nextLine();
   System.out.println("Qual plataforma?");
   String plataforma = scanner.nextLine();
   Ganho ganhos = new Ganho(valor, plataforma, LocalDate.now());
   ganhosList.add(ganhos);
   System.out.println("Deseja adicionar mais ganhos? (s/n)");
   resposta = scanner.nextLine();
 } while (resposta.equals("s"));
 for (Ganho ganho : ganhosList) {
   if(ganho.getPlataforma().equalsIgnoreCase("iFood")){
     totalifood += ganho.getValor();
   }  else
   total99 += ganho.getValor();

 }
    System.out.println("Total iFood: " + totalifood);
    System.out.println("Total 99: " + total99);
    System.out.println("Total: " + (totalifood + total99));
}

