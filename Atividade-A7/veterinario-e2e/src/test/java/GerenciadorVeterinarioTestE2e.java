import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GerenciadorVeterinarioTestE2e {

    private static final String BASE_URL = "http://localhost:8080";

    private WebDriver driver;
    private WebDriverWait wait;

    // Dados do veterinário compartilhados entre os testes
    private static final String NOME          = "Alexsandro Carvalho";
    private static final String ESPECIALIDADE = "Pequeno porte";
    private static final String EMAIL         = "aleca@email.com";
    private static final String SALARIO       = "3400";

    // --------------------------------------------------
    // Setup e Teardown
    // --------------------------------------------------

    @BeforeAll
    void setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeEach
    void irParaHome() {
        driver.get(BASE_URL + "/home");
        WebElement h1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        Assertions.assertEquals("Veterinarios", h1.getText());
    }

    @AfterAll
    void encerrarDriver() {
        if (driver != null) driver.quit();
    }

    // --------------------------------------------------
    // Métodos auxiliares
    // --------------------------------------------------

    private void preencherCampo(String id, String valor) {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        campo.clear();
        campo.sendKeys(valor);
    }

    private void clicarBotao(String texto) {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='" + texto + "']"))).click();
    }

    // Busca a linha na tabela que contém o nome informado
    private WebElement encontrarLinhaPorNome(String nome) {
        WebElement tabela = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("table.table.table-light")));

        List<WebElement> linhas = tabela.findElements(By.tagName("tr"));

        for (WebElement linha : linhas) {
            List<WebElement> colunas = linha.findElements(By.tagName("td"));
            if (!colunas.isEmpty() && colunas.get(0).getText().trim().equals(nome)) {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", linha);
                return linha;
            }
        }

        throw new NoSuchElementException("Veterinário não encontrado na tabela: " + nome);
    }

    // Verifica se alguma linha da tabela contém o nome informado
    private boolean veterinarioNaTabela(String nome) {
        WebElement tabela = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("table.table.table-light")));

        List<WebElement> linhas = tabela.findElements(By.tagName("tr"));

        return linhas.stream()
                .filter(linha -> !linha.findElements(By.tagName("td")).isEmpty())
                .anyMatch(linha -> linha.findElements(By.tagName("td"))
                        .get(0).getText().trim().equals(nome));
    }

    // --------------------------------------------------
    // TC-00 — Listar
    // --------------------------------------------------

    @Test
    @Order(0)
    @DisplayName("Deve exibir a página /home com navegação, tabela preenchida e botões de ação")
    void deveExibirPaginaHome() {

        // Assert — logo existe e redireciona para /home
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logo")));
        logo.click();
        wait.until(ExpectedConditions.urlContains("/home"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/home"));

        // Assert — nav com texto "HOME" existe e redireciona para /home
        WebElement navHome = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//nav//*[normalize-space()='Home']")));
        navHome.click();
        wait.until(ExpectedConditions.urlContains("/home"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/home"));

        // Assert — tabela não está vazia (deve ter mais de uma linha: cabeçalho + ao menos um registro)
        WebElement tabela = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("table.table.table-light")));

        List<WebElement> linhas = tabela.findElements(By.tagName("tr"));
        Assertions.assertTrue(linhas.size() > 1, "A tabela deve ter ao menos um registro além do cabeçalho");

        // Assert — th's do meio estão preenchidos (ignora o primeiro e o último, ambos vazios)
        WebElement tbody = tabela.findElement(By.tagName("tbody"));
        WebElement linhaCabecalho = tbody.findElements(By.tagName("tr")).get(0);
        List<WebElement> cabecalhos = linhaCabecalho.findElements(By.tagName("th"));
        for (int i = 1; i < cabecalhos.size() - 1; i++) {
            String textoTh = cabecalhos.get(i).getText().trim();
            Assertions.assertFalse(textoTh.isEmpty(), "O th na posição " + i + " não deve estar vazio");
        }

        // Assert — última célula da primeira linha de dados tem botão de editar e excluir
        WebElement primeiraLinhaData = linhas.get(1);
        List<WebElement> colunas = primeiraLinhaData.findElements(By.tagName("td"));
        WebElement ultimaCelula = colunas.get(colunas.size() - 1);

        Assertions.assertTrue(ultimaCelula.findElement(By.cssSelector(".btn.btn-warning")).isDisplayed());
        Assertions.assertTrue(ultimaCelula.findElement(By.cssSelector(".btn.btn-danger")).isDisplayed());

        // Assert — botões "Adicionar" e "Consultar" existem fora da tabela
        Assertions.assertTrue(driver.findElement(By.xpath("//button[normalize-space()='Adicionar']")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.xpath("//button[normalize-space()='Consultar']")).isDisplayed());
    }

    // --------------------------------------------------
    // TC-01 — Cadastrar
    // --------------------------------------------------

    @Test
    @Order(1)
    @DisplayName("Deve cadastrar um novo veterinário e exibi-lo na tabela")
    void deveCadastrarNovoVeterinario() {

        // Act — abrir formulário
        clicarBotao("Adicionar");

        wait.until(ExpectedConditions.urlContains("/form"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/form"));

        WebElement h1Form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        Assertions.assertEquals("Cadastrar novo veterinário", h1Form.getText());

        // Act — preencher e enviar
        preencherCampo("nome", NOME);
        preencherCampo("inputEspecialidade", ESPECIALIDADE);
        preencherCampo("inputEmail", EMAIL);
        preencherCampo("inputSalario", SALARIO);
        clicarBotao("Cadastrar");

        // Assert — registro aparece na última linha da tabela
        wait.until(ExpectedConditions.urlContains("/home"));

        WebElement tabela = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("table.table.table-light")));

        List<WebElement> linhas = tabela.findElements(By.tagName("tr"));
        WebElement ultimaLinha = linhas.get(linhas.size() - 1);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", ultimaLinha);

        List<WebElement> colunas = ultimaLinha.findElements(By.tagName("td"));

        Assertions.assertEquals(NOME,          colunas.get(0).getText().trim());
        Assertions.assertEquals(ESPECIALIDADE,  colunas.get(1).getText().trim());
        Assertions.assertEquals(EMAIL,          colunas.get(2).getText().trim());
        Assertions.assertTrue(colunas.get(3).getText().trim().contains("R$"));
        Assertions.assertTrue(colunas.get(3).getText().trim().contains(SALARIO));
    }

    // --------------------------------------------------
    // TC-02 — Pesquisar
    // --------------------------------------------------

    @Test
    @Order(2)
    @DisplayName("Deve pesquisar o veterinário cadastrado e exibi-lo na tabela")
    void devePesquisarVeterinario() {

        // Act — abrir página de pesquisa e buscar
        clicarBotao("Consultar");
        wait.until(ExpectedConditions.urlContains("/find"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/find"));

        preencherCampo("nome", NOME);
        clicarBotao("Consultar");

        // Assert — nome exato aparece em ao menos uma linha dos resultados
        Assertions.assertTrue(veterinarioNaTabela(NOME),
                "Veterinário '" + NOME + "' deve aparecer nos resultados");
    }

    // --------------------------------------------------
    // TC-03 — Editar
    // --------------------------------------------------

    @Test
    @Order(3)
    @DisplayName("Deve editar o veterinário cadastrado")
    void deveEditarVeterinario() {

        // Arrange — dados atualizados
        String nomeEditado          = NOME          + " editado";
        String especialidadeEditada = ESPECIALIDADE + " editado";
        String emailEditado         = "editado." + EMAIL;
        int salarioEditado          = Integer.parseInt(SALARIO) + 1000;

        // Act — pesquisar e clicar no botão de editar
        clicarBotao("Consultar");
        wait.until(ExpectedConditions.urlContains("/find"));
        preencherCampo("nome", NOME);
        clicarBotao("Consultar");

        WebElement linha = encontrarLinhaPorNome(NOME);
        linha.findElement(By.cssSelector("td:last-child .btn.btn-warning .fa.fa-pencil")).click();

        // Assert — redirecionou para /form/{id} com inputs preenchidos
        wait.until(ExpectedConditions.urlMatches(".*/form/\\d+"));
        Assertions.assertTrue(driver.getCurrentUrl().matches(".*/form/\\d+"));

        Assertions.assertEquals(NOME,          driver.findElement(By.id("nome")).getAttribute("value"));
        Assertions.assertEquals(ESPECIALIDADE,  driver.findElement(By.id("inputEspecialidade")).getAttribute("value"));
        Assertions.assertEquals(EMAIL,          driver.findElement(By.id("inputEmail")).getAttribute("value"));
        Assertions.assertTrue(driver.findElement(By.id("inputSalario")).getAttribute("value").contains(SALARIO));

        // Act — editar os campos e salvar
        preencherCampo("nome", nomeEditado);
        preencherCampo("inputEspecialidade", especialidadeEditada);
        preencherCampo("inputEmail", emailEditado);
        preencherCampo("inputSalario", String.valueOf(salarioEditado));
        clicarBotao("Atualizar");

        // Assert — voltou para /home e veterinário atualizado aparece na tabela
        wait.until(ExpectedConditions.urlContains("/home"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/home"));

        WebElement linhaAtualizada = encontrarLinhaPorNome(nomeEditado);
        List<WebElement> colunas = linhaAtualizada.findElements(By.tagName("td"));

        Assertions.assertEquals(nomeEditado,          colunas.get(0).getText().trim());
        Assertions.assertEquals(especialidadeEditada,  colunas.get(1).getText().trim());
        Assertions.assertEquals(emailEditado,          colunas.get(2).getText().trim());
        Assertions.assertTrue(colunas.get(3).getText().trim().contains("R$"));
        Assertions.assertTrue(colunas.get(3).getText().trim().contains(String.valueOf(salarioEditado)));
    }

    // --------------------------------------------------
    // TC-04 — Excluir
    // --------------------------------------------------

    @Test
    @Order(4)
    @DisplayName("Deve excluir o veterinário editado e removê-lo da tabela")
    void deveExcluirVeterinario() {

        // Arrange — nome após a edição do TC-03
        String nomeEditado = NOME + " editado";

        // Act — pesquisar o veterinário editado
        clicarBotao("Consultar");
        wait.until(ExpectedConditions.urlContains("/find"));
        preencherCampo("nome", nomeEditado);
        clicarBotao("Consultar");

        // Act — clicar no botão de excluir na linha do veterinário
        WebElement linha = encontrarLinhaPorNome(nomeEditado);
        WebElement botaoExcluir = linha.findElement(By.cssSelector("td:last-child .btn.btn-danger"));
        botaoExcluir.click();

        // Assert — página recarregou (URL permanece em /find ou redireciona para /home)
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/find"),
                ExpectedConditions.urlContains("/home")
        ));

        // Assert — veterinário não aparece mais na tabela
        Assertions.assertFalse(veterinarioNaTabela(nomeEditado),
                "Veterinário excluído não deve aparecer na tabela");
    }
}