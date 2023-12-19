package br.edu.ifsp.arq.tsi.inoo.locacao.view;

import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

import br.edu.ifsp.arq.tsi.inoo.locacao.model.Carro;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.Locacao;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.Pessoa;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.PessoaFisica;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.PessoaJuridica;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        // Listas para armazenar clientes, carros e locações
        ArrayList<PessoaFisica> pessoasFisicasLocacao = new ArrayList<>();
        ArrayList<PessoaJuridica> pessoasJuridicasLocacao = new ArrayList<>();
        ArrayList<Carro> carrosLocacao = new ArrayList<>();
        ArrayList<Locacao> locacoes = new ArrayList<>();

        // Menu de opções
        while (continuar) {
            System.out.println("\nBem-vindo! O que você deseja fazer?");
            System.out.println("\n--Cliente--");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Remover cliente");
            System.out.println("3. Pesquisar cliente");
            System.out.println("\n--Carro--");
            System.out.println("4. Cadastrar carro");
            System.out.println("5. Remover carro");
            System.out.println("6. Pesquisar carro");
            System.out.println("\n--Locação e Devolução--");
            System.out.println("7. Realizar Locação");
            System.out.println("8. Pesquisar Locação");
            System.out.println("9. Realizar Devolução");
            System.out.println("\n--Relatórios--");
            System.out.println("10. Opções de Relatório");
            System.out.println("\n--Sair do Programa--");
            System.out.println("11. Sair");

            System.out.println("\nEscolha uma opção:");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            // Opções de Menu
            switch (opcao) {
                case 1:
                    cadastrarCliente(pessoasFisicasLocacao, pessoasJuridicasLocacao, scanner);
                    break;
                case 2:
                    removerCliente(pessoasFisicasLocacao, pessoasJuridicasLocacao, scanner);
                    break;
                case 3:
                    pesquisarCliente(pessoasFisicasLocacao, pessoasJuridicasLocacao, scanner);
                    break;
                case 4:
                    cadastrarCarro(carrosLocacao, scanner);
                    break;
                case 5:
                    removerCarro(carrosLocacao, scanner);
                    break;
                case 6:
                    pesquisarCarro(carrosLocacao, scanner);
                    break;
                case 7:
                    realizarLocacao(pessoasFisicasLocacao, pessoasJuridicasLocacao, carrosLocacao, locacoes, scanner);
                    break;
                case 8:
                    pesquisarLocacao(locacoes, scanner);
                    break;

                case 9:
                    realizarDevolucao(scanner, locacoes);
                    break;
                case 10:
                    exibirRelatorio(scanner, pessoasFisicasLocacao, pessoasJuridicasLocacao, carrosLocacao, locacoes);
                    break;
                case 11:
                    System.out.println("Saindo do programa...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida!");
                    break;
            }
        }
        scanner.close();
    }

    // Função para cadastrar um cliente (pessoa física ou jurídica)
    public static void cadastrarCliente(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao, Scanner scanner) {
        int opcao;
        System.out.println("\n----------");
        System.out.println("Selecione o tipo do Cliente:");
        System.out.println("1. Físico \n2. Jurídico");
        opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (opcao == 1) {
            boolean cpfDuplicado = true;
            while (cpfDuplicado) {
                System.out.println("\n----------");
                System.out.println("--Cadastro Pessoa Física--");
                System.out.println("\nDigite o nome da Pessoa Física:");
                String nomePF = scanner.nextLine();
                System.out.println("Digite o CPF da Pessoa Física:");
                String cpf = scanner.nextLine();

                if (verificarCpfDuplicado(pessoasFisicasLocacao, cpf)) { // Correção aqui
                    System.out.println("\nCPF já cadastrado! Por favor, insira um CPF diferente!");
                } else {
                    try {
                        PessoaFisica pessoaFisica = new PessoaFisica(nomePF, cpf);
                        pessoasFisicasLocacao.add(pessoaFisica);
                        cpfDuplicado = false;
                        System.out.println("\n--Situação de Cadastro--");
                        System.out.println("Pessoa cadastrada com sucesso!");
                        System.out.println("Código do cliente: " + pessoaFisica.getNome() + " = "
                                + pessoaFisica.getProximoCodigo());
                        System.out.println("----------");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                        System.out.println("Por favor, digite um CPF válido!");
                    }
                }
            }
        } else if (opcao == 2) {
            boolean cnpjDuplicado = true;
            while (cnpjDuplicado) {
                System.out.println("\n----------");
                System.out.println("--Cadastro Pessoa Jurídica--");
                System.out.println("\nDigite o nome da Pessoa Jurídica:");
                String nomePJ = scanner.nextLine();
                System.out.println("Digite o CNPJ da Pessoa Jurídica:");
                String cnpj = scanner.nextLine();

                // Verificar se o CPNJ já foi cadastrado
                if (verificarCnpjDuplicado(pessoasJuridicasLocacao, cnpj)) {
                    System.out.println("\nCNPJ já cadastrado! Por favor, insira um CNPJ diferente!");
                } else {
                    System.out.println("Digite a razão social:");
                    String nomeEmpresa = scanner.nextLine();
                    PessoaJuridica pessoaJuridica = new PessoaJuridica(nomePJ, cnpj, nomeEmpresa);
                    pessoasJuridicasLocacao.add(pessoaJuridica);
                    System.out.println("----------");

                    System.out.println("\n--Situação de Cadastro--");
                    System.out.println("Pessoa cadastrada com sucesso!");
                    System.out.println("Código do cliente: " + pessoaJuridica.getNome() + " = "
                            + pessoaJuridica.getProximoCodigo());
                    cnpjDuplicado = false;
                    System.out.println("----------");
                }
            }
        }
    }

    private static boolean verificarCpfDuplicado(ArrayList<PessoaFisica> pessoas, String cpf) {
        for (PessoaFisica pessoa : pessoas) {
            if (pessoa.getCpf().equals(cpf)) {
                return true; // CPF já existe na lista
            }
        }
        return false; // CPF não existe na lista
    }

    // Remove o cliente pelo Id dele
    public static void removerCliente(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao, Scanner scanner) {
        System.out.println("\n----------");
        System.out.println("Remover Cliente do sistema:\n");
        System.out.print("Digite o código do cliente a ser removido: ");
        int codigoCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        boolean clienteRemovido = false;

        PessoaFisica clientePFRemover = null;
        for (PessoaFisica pessoa : pessoasFisicasLocacao) {
            if (pessoa.getProximoCodigo() == codigoCliente) {
                clientePFRemover = pessoa;
                clienteRemovido = true;
                break;
            }
        }

        if (!clienteRemovido) {
            PessoaJuridica clientePJRemover = null;
            for (PessoaJuridica pessoa : pessoasJuridicasLocacao) {
                if (pessoa.getProximoCodigo() == codigoCliente) {
                    clientePJRemover = pessoa;
                    clienteRemovido = true;
                    break;
                }
            }
            if (clientePJRemover != null) {
                pessoasJuridicasLocacao.remove(clientePJRemover);
                System.out.println("\n----------");
                System.out.println("Cliente removido com sucesso!!!");
                System.out.println("----------");
            }
        } else {
            if (clientePFRemover != null) {
                pessoasFisicasLocacao.remove(clientePFRemover);
                System.out.println("\n----------");
                System.out.println("Cliente removido com sucesso!!!");
                System.out.println("----------");
            }
        }

        if (!clienteRemovido) {
            System.out.println("\nErro ao remover o cliente!!! Verifique o código digitado!");
        }
    }

    // Pesquisa pelo Id
    public static void pesquisarCliente(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao, Scanner scanner) {
        System.out.println("\n----------");
        System.out.print("Digite o código do cliente que deseja encontrar: ");
        int codigoCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        boolean clienteEncontrado = false;

        // Encontrado - Pessoa Física
        for (PessoaFisica pessoaFisica : pessoasFisicasLocacao) {
            if (pessoaFisica.getProximoCodigo() == codigoCliente) {
                System.out.println("\n----------");
                System.out.println("Cliente Físico encontrado: ");
                System.out.println("Nome: " + pessoaFisica.getNome());
                System.out.println("CPF: " + pessoaFisica.getCpf());
                System.out.println("----------");
                clienteEncontrado = true;
                break;
            }
        }

        // Encontrado - Pessoa Jurídica
        if (!clienteEncontrado) {
            for (PessoaJuridica pessoaJuridica : pessoasJuridicasLocacao) {
                if (pessoaJuridica.getProximoCodigo() == codigoCliente) {
                    System.out.println("\n----------");
                    System.out.println("\nCliente Jurídico encontrado: ");
                    System.out.println("Nome: " + pessoaJuridica.getNome());
                    System.out.println("CNPJ: " + pessoaJuridica.getCnpj());
                    System.out.println("Razão Social: " + pessoaJuridica.getRazaoSocial());
                    System.out.println("----------");
                    clienteEncontrado = true;
                    break;
                }
            }
        }

        if (!clienteEncontrado) {
            System.out.println("Cliente não encontrado!!!");
        }
    }

    // Cadastro de carro
    public static void cadastrarCarro(ArrayList<Carro> carrosLocacao, Scanner scanner) {
        System.out.println("\n----------");
        System.out.println("-- Cadastro do Carro --");
        System.out.println("\nDigite a marca do carro:");
        String marca = scanner.nextLine();
        System.out.println("Digite o modelo do carro:");
        String modelo = scanner.nextLine();
        System.out.println("Digite o ano do carro:");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer
        System.out.println("Digite a placa do carro:");
        String placa = scanner.nextLine();
        System.out.println("Digite a quantidade de portas do carro:");
        int quantidadePortas = scanner.nextInt();
        System.out.println("Digite o valor da diária do carro:");
        double valorDiaria = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer
        System.out.println("O carro possui ar condicionado? (S/N):");
        String temArCondicionado = scanner.nextLine();
        boolean arCondicionado = temArCondicionado.equalsIgnoreCase("S");

        Carro carro = new Carro(1, marca, modelo, ano, placa, ano, arCondicionado, valorDiaria, true);
        carrosLocacao.add(carro);
        System.out.println("\n--Situação de Cadastro--");
        System.out.println("Carro cadastrado com sucesso!");
        System.out.println("Modelo: " + carro.getModelo() + " - Placa: " + carro.getPlaca() + " - Código: "
                + carro.getCarroCodigo());
        System.out.println("----------");
    }

    // Remover o Carro
    public static void removerCarro(ArrayList<Carro> carrosLocacao, Scanner scanner) {
        System.out.println("Remover Carro do sistema:\n");
        System.out.print("Digite o código do carro a ser removido: ");
        int codigoCarro = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        boolean carroRemovido = false;

        for (Carro carro : carrosLocacao) {
            if (carro.getCarroCodigo() == codigoCarro) {
                carrosLocacao.remove(carro);
                carroRemovido = true;
                System.out.println("\n----------");
                System.out.println("Carro removido com sucesso!!!");
                System.out.println("----------");
                break;
            }
        }

        if (!carroRemovido) {
            System.out.println("\n----------");
            System.out.println("Erro ao remover o carro!!! Verifique o código digitado!");
            System.out.println("----------");
        }
    }

    // Pesquisar Carro pelo Id
    public static void pesquisarCarro(ArrayList<Carro> carrosLocacao, Scanner scanner) {
        System.out.println("\n----------");
        System.out.print("Digite o código do carro que deseja encontrar: ");
        int codigoCarro = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        boolean carroEncontrado = false;

        for (Carro carro : carrosLocacao) {
            if (carro.getCarroCodigo() == codigoCarro) {
                System.out.println("\n----------");
                System.out.println("Carro encontrado: ");
                System.out.println("Modelo: " + carro.getModelo());
                System.out.println("Placa: " + carro.getPlaca());
                System.out.println("Ano: " + carro.getAno());
                System.out.println("Quantidade de portas: " + carro.getQuantidadePortas());
                System.out.println("Valor da Diária: " + carro.getValorDiaria());
                System.out.println("Ar Condicionado: " + (carro.isArCondicionado() ? "Sim" : "Não"));
                System.out.println("----------");
                carroEncontrado = true;
                break;
            }
        }

        if (!carroEncontrado) {
            System.out.println("\n----------");
            System.out.println("Carro não encontrado!!!");
            System.out.println("----------");
        }
    }

    // Cadastrar Locação
    public static void realizarLocacao(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao,
            ArrayList<Carro> carrosLocacao,
            ArrayList<Locacao> locacoes,
            Scanner scanner) {
        System.out.println("\n----------");
        System.out.println("Selecione o tipo de cliente para a locação:");
        System.out.println("1. Pessoa Física");
        System.out.println("2. Pessoa Jurídica");
        int tipoCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        ArrayList<? extends Pessoa> clientesLocacao = (tipoCliente == 1) ? pessoasFisicasLocacao
                : pessoasJuridicasLocacao;
        System.out.println("\n----------");
        System.out.println("Selecione a Pessoa para a locação:");
        for (int i = 0; i < clientesLocacao.size(); i++) {
            Pessoa cliente = clientesLocacao.get(i);
            System.out.println((i + 1) + ". " + cliente.getNome());
            System.out.println("----------");
        }

        int indexCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("\n----------");
        System.out.println("Carros Disponíveis:");
        int index = 0;
        for (Carro carro : carrosLocacao) {
            if (carro.isDisponivel()) {
                System.out.println((index + 1) + ". " + carro.getModelo() + " - Disponível: " +
                        (carro.isDisponivel() ? "Sim" : "Não"));
                index++;
            }
        }

        if (index == 0) {
            System.out.println("\n----------");
            System.out.println("Nenhum carro disponível para locação no momento!");
            System.out.println("----------");
            return;
        }
        System.out.println("----------");
        System.out.println("Selecione o carro desejado:");
        int indexCarro = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (indexCarro <= index && indexCarro > 0) {
            Carro carroSelecionado = carrosLocacao.get(indexCarro - 1);

            LocalDate dataRealizacao = LocalDate.now();

            System.out.println("Informe quantidade de dias que o carro ficará alugado: ");
            int numeroDiarias = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            Locacao novaLocacao;
            Pessoa cliente = clientesLocacao.get(indexCliente - 1);
            if (tipoCliente == 1) {
                PessoaFisica pessoa = (PessoaFisica) cliente;
                novaLocacao = new Locacao(pessoa, carroSelecionado, dataRealizacao, numeroDiarias);
            } else {
                PessoaJuridica pessoa = (PessoaJuridica) cliente;
                novaLocacao = new Locacao(pessoa, carroSelecionado, dataRealizacao, numeroDiarias);
            }

            locacoes.add(novaLocacao);

            carroSelecionado.setDisponivel(false);
            System.out.println("\n----------");
            System.out.println("Locação realizada com sucesso para: " + cliente.getNome() +
                    " - Número da Locação: " + novaLocacao.getNumero() +
                    " - Carro: " + carroSelecionado.getModelo() +
                    " - Data de realização: " + dataRealizacao +
                    " - Data máxima devolução: " + novaLocacao.getDataDevolucaoMaxima());
            System.out.println("----------");
        } else {
            System.out.println("\n----------");
            System.out.println("Erro ao realizar a Locação!");
            System.out.println("----------");
        }
    }

    // Pesquisar pelo Id
    public static void pesquisarLocacao(ArrayList<Locacao> locacoes, Scanner scanner) {
        System.out.println("\n----------");
        System.out.print("Digite o número da locação que deseja pesquisar: ");
        int numeroLocacao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Locacao locacaoEncontrada = buscarLocacaoPeloNumero(locacoes, numeroLocacao);

        if (locacaoEncontrada != null) {
            System.out.println("\n----------");
            System.out.println("Locação encontrada:");
            System.out.println("Número: " + locacaoEncontrada.getNumero());
            System.out.println("Cliente: " + locacaoEncontrada.getPessoa().getNome());
            System.out.println("Carro: " + locacaoEncontrada.getCarro().getModelo());
            System.out.println("Data de realização: " + locacaoEncontrada.getDataRealizacao());
            System.out.println("Número de dias alugado: " + locacaoEncontrada.getnDiarias());
            System.out.println("Data de devolução máxima: " + locacaoEncontrada.getDataDevolucaoMaxima());
            System.out.println("----------");
        } else {
            System.out.println("Locação não encontrada com o número fornecido!");
        }
    }

    private static Locacao buscarLocacaoPeloNumero(ArrayList<Locacao> locacoes, int numeroLocacao) {
        for (Locacao locacao : locacoes) {
            if (locacao.getNumero() == numeroLocacao) {
                return locacao;
            }
        }
        return null;
    }

    // Realiza a devolução
    // Realiza a devolução
    public static void realizarDevolucao(Scanner scanner, ArrayList<Locacao> locacoes) {
        System.out.println("\n----------");
        System.out.println("-- Devolução --");
        System.out.print("\nDigite o número da locação que deseja devolver: ");
        int numeroLocacao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Locacao locacaoParaDevolucao = buscarLocacaoPeloNumero(locacoes, numeroLocacao);

        if (locacaoParaDevolucao != null) {
            System.out.print("Digite a data da devolução (AAAA-MM-DD): ");
            String dataDevolucaoStr = scanner.nextLine();
            LocalDate dataDevolucao = LocalDate.parse(dataDevolucaoStr);

            // Verificar se houve atraso na devolução
            if (dataDevolucao.isAfter(locacaoParaDevolucao.getDataDevolucaoMaxima())) {
                System.out.println("Aviso: Devolução com atraso sujeito a taxa extra!!");
            }

            double valorTotal = locacaoParaDevolucao.calcularValorTotal(dataDevolucao);

            if (valorTotal > 0) {
                System.out.println("Placa do carro: " + locacaoParaDevolucao.getCarro().getPlaca()); // Exibe a placa                                                                                      // do carro
                System.out.println("Valor total da locação: " + valorTotal);
                System.out.println("----------");

                locacaoParaDevolucao.realizarDevolucao(dataDevolucao);
                System.out.println("\n--Situação da Devolução--");
                System.out.println("Devolução realizada com sucesso!! Para a locação de número: " + numeroLocacao);
                System.out.println("----------");
            } else {
                System.out.println("Erro ao calcular o valor total da locação!");
            }
        } else {
            System.out.println("Locação não encontrada com o número fornecido!");
        }
    }

    /*
     * private static Locacao buscarLocacaoPeloCodigo(ArrayList<Locacao> locacoes,
     * int numeroLocacao) {
     * for (Locacao locacao : locacoes) {
     * if (locacao.getNumero() == numeroLocacao) {
     * return locacao;
     * }
     * }
     * return null;
     * }
     */
    // Mostra as opções de Relatórios
    public static void exibirRelatorio(Scanner scanner, ArrayList<PessoaFisica> pessoasFisicas,
            ArrayList<PessoaJuridica> pessoasJuridicas, ArrayList<Carro> carros, ArrayList<Locacao> locacoes) {
        System.out.println("\n----------");
        System.out.println("--Opções de Relatório--");
        System.out.println("1 - Clientes  \n2 - Carros  \n3 - Locações \n");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                exibirRelatorioClientes(pessoasFisicas, pessoasJuridicas);
                break;
            case 2:
                exibirRelatorioCarros(carros);
                break;
            case 3:
                exibirRelatorioLocacoes(locacoes);
                break;
            default:
                System.out.println("Opção inválida!!! Tente novamente!");
        }
    }

    // Relatório de Carros
    public static void exibirRelatorioCarros(ArrayList<Carro> carrosLocacao) {
        System.out.println("\n--Carros--");
        System.out.println("Relatório de Carros cadastrados: ");
        for (Carro carro : carrosLocacao) {
            System.out.println("\nCódigo: " + carro.getCarroCodigo());
            System.out.println("Marca: " + carro.getMarca());
            System.out.println("Modelo: " + carro.getModelo());
            System.out.println("Ano: " + carro.getAno());
            System.out.println("Placa: " + carro.getPlaca());
            System.out.println("Quantidade de portas: " + carro.getQuantidadePortas());
            System.out.println("Ar condicionado: " + carro.isArCondicionado());
            System.out.println("Valor da diária: " + carro.getValorDiaria());
            System.out.println("Estado: " + (carro.isDisponivel() ? "Disponível" : "Indisponível") + "");
            System.out.println("----------");
        }
    }

    // Relatório de Clientes
    public static void exibirRelatorioClientes(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao) {
        System.out.println("--Escolha o tipo de Cliente--");
        System.out.println("1. Físico\n2. Jurídico \n");
        Scanner teclado = new Scanner(System.in);
        int opcao = teclado.nextInt();

        if (opcao == 1) {
            System.out.println("\n--Cliente Físico--");
            System.out.println("Relatório de Clientes Físicos cadastrados: ");
            for (PessoaFisica pessoaFisica : pessoasFisicasLocacao) {
                System.out.println("Código: " + pessoaFisica.getProximoCodigo());
                System.out.println("Nome: " + pessoaFisica.getNome());
                System.out.println("CPF: " + pessoaFisica.getCpf());
                System.out.println("----------");
            }
        } else if (opcao == 2) {
            System.out.println("\n--Cliente Jurídico--");
            System.out.println("Relatório de Clientes Jurídicos cadastrados: ");
            for (PessoaJuridica pessoaJuridica : pessoasJuridicasLocacao) {
                System.out.println("Código: " + pessoaJuridica.getProximoCodigo());
                System.out.println("Nome: " + pessoaJuridica.getNome());
                System.out.println("CNPJ: " + pessoaJuridica.getCnpj());
                System.out.println("Razão Social: " + pessoaJuridica.getRazaoSocial());
                System.out.println("----------");
            }
        } else {
            System.out.println("\n----------");
            System.out.println("Opção inválida!");
            System.out.println("----------");
        }
    }

    private static boolean verificarCnpjDuplicado(ArrayList<PessoaJuridica> pessoas, String cnpj) {
        for (PessoaJuridica pessoa : pessoas) {
            if (pessoa.getCnpj().equals(cnpj)) {
                return true; // CNPJ já existe na lista
            }
        }
        return false; // CNPJ não existe na lista
    }

    // Relatório Locação
    public static void exibirRelatorioLocacoes(ArrayList<Locacao> locacoes) {
        System.out.println("\n--Locações--");
        System.out.println("Relatório de Locações feitas: ");
        for (Locacao locacao : locacoes) {
            System.out.println("\nNúmero: " + locacao.getNumero());
            System.out.println("Cliente: " + locacao.getPessoa().getNome());
            System.out.println("Carro: " + locacao.getCarro().getModelo());
            System.out.println("Data da realização: " + locacao.getDataRealizacao());
            System.out.println("Número de dias alugado: " + locacao.getnDiarias());
            System.out.println("Data da devolução: " + locacao.getDataDevolucaoMaxima());
            System.out.println("----------");
        }
    }
}