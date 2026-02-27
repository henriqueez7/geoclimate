aplicarTemaPorHorario();

async function buscarCep() {
    const cepInput = document.getElementById("cepInput");
    const cep = cepInput.value.replace(/\D/g, "");
    const erroEl = document.getElementById("erro");
    const resultadoEl = document.getElementById("resultado");
    const loader = document.getElementById("loader");
    const btn = document.getElementById("buscarBtn");

    erroEl.classList.add("hidden");
    resultadoEl.classList.add("hidden");

    if (cep.length !== 8) {
        erroEl.textContent = "Digite um CEP válido com 8 números.";
        erroEl.classList.remove("hidden");
        return;
    }

    loader.classList.remove("hidden");
    btn.disabled = true;

    try {
        const response = await fetch(`/api/geoclimate/${cep}`);

        if (!response.ok) {
            throw new Error();
        }

        const data = await response.json();
        preencherDados(data);
        aplicarEfeitoClima(data.clima.descricao);

    } catch {
        erroEl.textContent = "Erro ao buscar informações.";
        erroEl.classList.remove("hidden");
    } finally {
        loader.classList.add("hidden");
        btn.disabled = false;
    }
}

function preencherDados(data) {
    document.getElementById("cidade").textContent =
        `${data.cidade} - ${data.estado}`;

    document.getElementById("rua").textContent =
        data.logradouro || "Rua não informada";

    document.getElementById("bairro").textContent =
        data.bairro || "Bairro não informado";

    document.getElementById("temperatura").textContent =
        data.clima.temperatura.toFixed(1);

    document.getElementById("descricao").textContent =
        data.clima.descricao;

    document.getElementById("umidade").textContent =
        data.clima.umidade;

    document.getElementById("resultado").classList.remove("hidden");
}

function aplicarTemaPorHorario() {
    const hora = new Date().getHours();
    document.body.classList.remove("day", "night");

    if (hora >= 6 && hora < 18) {
        document.body.classList.add("day");
    } else {
        document.body.classList.add("night");
    }
}

function aplicarEfeitoClima(descricao) {
    const body = document.body;
    body.classList.remove("rain", "cloudy", "clear");

    const d = descricao.toLowerCase();

    if (d.includes("chuva")) {
        body.classList.add("rain");
    } else if (d.includes("nuvem") || d.includes("nublado")) {
        body.classList.add("cloudy");
    } else {
        body.classList.add("clear");
    }
}