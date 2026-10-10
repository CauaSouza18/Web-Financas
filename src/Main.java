import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
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
    int id = Math.floorMod(UUID.randomUUID().hashCode(), Integer.MAX_VALUE) + 1;

    Usuario user = new Usuario(nome, email, senha, id);

    System.out.println("ID: " + user.getId());
    System.out.println("Nome: " + user.getNome());
    System.out.println("Email: " + user.getEmail());
    System.out.println("Senha: " + user.getSenha());

    ArrayList<Ganho> ganhosList = new ArrayList<>();
    File arquivoGanhos = new File("ganhos.txt");
    int idGanho = 0;
    if (arquivoGanhos.exists()) {
      try (Scanner leitor = new Scanner(arquivoGanhos)) {
        while (leitor.hasNextLine()) {
          String linha = leitor.nextLine().trim();
          if (linha.isBlank()) {
            continue;
          }
       
          String[] partes = linha.split(";", -1);
          if (partes.length != 4) {
            continue;
        
          }
          try {
            double valorSalvo = Double.parseDouble(partes[0].trim());
            String plataformaSalva = partes[1].trim();
            LocalDate dataSalva = LocalDate.parse(partes[2].trim());
            int idSalvo = Integer.parseInt(partes[3].trim());
             if (idSalvo > idGanho) {
            idGanho = idSalvo;
          }
            ganhosList.add(new Ganho(idSalvo, valorSalvo, plataformaSalva, dataSalva));
          } catch (NumberFormatException | java.time.format.DateTimeParseException e) {
            // Ignora linhas inválidas do arquivo e continua lendo os demais registros.
          }
        }
      }
    }
    int opcao;
    do {
      System.out.println("1 - Cadastrar ganho");
      System.out.println("2 - Sair");
      opcao = scanner.nextInt();
      scanner.nextLine(); // limpa a sobra do Enter

      switch (opcao) {
        case 1:
          System.out.println("Diga o ganho do dia");
          double valor = scanner.nextDouble();
          scanner.nextLine();
          System.out.println("Qual plataforma?");
          String plataforma = scanner.nextLine();

          idGanho++;
          Ganho novoGanho = new Ganho(idGanho, valor, plataforma, LocalDate.now());
          ganhosList.add(novoGanho);

          try (FileWriter filewriter = new FileWriter("ganhos.txt", true)) {
            filewriter.write(novoGanho.getValor() + ";" + novoGanho.getPlataforma() + ";"
                + novoGanho.getData() + ";" + novoGanho.getId() + "\n");
          }
          break;
        case 2:
          System.out.println("Saindo do programa...");
          break;
        default:
          System.out.println("Opção inválida");
      }
    } while (opcao != 2);

    double totalifood = 0;
    double total99 = 0;
    for (Ganho ganho : ganhosList) {
      if (ganho.getPlataforma().equalsIgnoreCase("iFood")) {
        totalifood += ganho.getValor();
      } else {
        total99 += ganho.getValor();
      }
    }

    System.out.println("Total iFood: " + totalifood);
    System.out.println("Total 99: " + total99);
    System.out.println("Total: " + (totalifood + total99));
  }
}
