import java.util.List;
import java.util.Locale;
 
public class lojaOnline {
 
    public static void main(String[] args) {
        List<Item> itens = List.of(
                new Item("Teclado", 150.00, 1),
                new Item("Mouse", 50.00, 2));
 
        Carrinho carrinhoVip = new Carrinho(itens, true);
        Carrinho carrinhoComum = new Carrinho(itens, false);
 
        System.out.println(new EmailPromocional().mensagem("Lucas"));
        System.out.println(String.format(Locale.US, "Total do carrinho VIP: R$ %.2f", carrinhoVip.total()));
        System.out.println(new NotaFiscal().gerar(carrinhoVip));
        System.out.println(String.format(Locale.US, "Economia total dos VIPs: R$ %.2f",
                new Relatorio().economiaDosClientesVip(List.of(carrinhoVip, carrinhoComum))));
    }
}
 
class PoliticaDesconto {
 
    static final double PERCENTUAL_VIP = 0.10; 
    private PoliticaDesconto() {
    }
 
    static double calcular(double subtotal, boolean clienteVip) {
        return clienteVip ? subtotal * PERCENTUAL_VIP : 0.0;
    }
 
    static String descricao() {
        return String.format(Locale.US, "%.0f%%", PERCENTUAL_VIP * 100);
    }
}
 
class Item {
    final String nome;
    final double preco;
    final int qtd;
 
    Item(String nome, double preco, int qtd) {
        this.nome = nome;
        this.preco = preco;
        this.qtd = qtd;
    }
}
 
class Carrinho {
    final List<Item> itens;
    final boolean clienteVip;
 
    Carrinho(List<Item> itens, boolean clienteVip) {
        this.itens = itens;
        this.clienteVip = clienteVip;
    }
 
    double subtotal() {
        double soma = 0;
        for (Item item : itens) {
            soma += item.preco * item.qtd;
        }
        return soma;
    }
 
    double desconto() {
        return PoliticaDesconto.calcular(subtotal(), clienteVip);
    }
 
    double total() {
        return subtotal() - desconto();
    }
}
 
class NotaFiscal {
    String gerar(Carrinho carrinho) {
        return String.format(Locale.US,
                "NOTA FISCAL | Subtotal: R$ %.2f | Desconto VIP (%s): R$ %.2f | Total: R$ %.2f",
                carrinho.subtotal(), PoliticaDesconto.descricao(), carrinho.desconto(), carrinho.total());
    }
}
 
class Relatorio {
    double economiaDosClientesVip(List<Carrinho> carrinhos) {
        double economia = 0;
        for (Carrinho carrinho : carrinhos) {
            economia += carrinho.desconto();
        }
        return economia;
    }
}
 
class EmailPromocional {
    String mensagem(String nome) {
        return "Olá, " + nome + "! Como cliente VIP, você tem "
                + PoliticaDesconto.descricao() + " de desconto em todas as compras.";
    }
}