import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import model.Ganho;
import model.Usuario;

public class Main {
public static void main(String[] args) throws IOException {

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
    File arquivoGanhos = new File("ganhos.txt");
    if (arquivoGanhos.exists()) {
      try (Scanner leitor = new Scanner(arquivoGanhos)) {
        while (leitor.hasNextLine()) {
          String linha = leitor.nextLine().trim();
          if (linha.isBlank()) {
            continue;
          }
          String[] partes = linha.split(";", -1);
          if (partes.length != 3) {
            continue;
          }
          try {
            double valorSalvo = Double.parseDouble(partes[0].trim());
            String plataformaSalva = partes[1].trim();
            LocalDate dataSalva = LocalDate.parse(partes[2].trim());
            ganhosList.add(new Ganho(valorSalvo, plataformaSalva, dataSalva));
          } catch (NumberFormatException | java.time.format.DateTimeParseException e) {
            // Ignora linhas inválidas do arquivo e continua lendo os demais registros.
          }
        }
      }
    }
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
      try (FileWriter filewriter = new FileWriter("ganhos.txt", true)) {
          filewriter.write(ganhos.getValor() + ";" + ganhos.getPlataforma() + ";" + ganhos.getData() + "\n");
      }
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
}

