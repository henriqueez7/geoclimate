console.log("JS carregado");

async function buscarCep() {

const cep = document.getElementById("cepInput").value;

const resultado = document.getElementById("resultado");
const erro = document.getElementById("erro");
const loader = document.getElementById("loader");

erro.classList.add("hidden");
resultado.classList.add("hidden");

if (!cep || cep.length !== 8) {

erro.textContent = "Digite um CEP válido com 8 números";
erro.classList.remove("hidden");
return;

}

loader.classList.remove("hidden");

try {

const response = await fetch(`/api/geoclimate/${cep}`);

if (!response.ok) {
throw new Error("Erro na API");
}

const data = await response.json();

if (!data || !data.clima) {
throw new Error("Resposta inválida da API");
}

preencherDados(data);

carregarHistorico();

} catch (e) {

console.error(e);

erro.textContent = "Erro ao buscar CEP";
erro.classList.remove("hidden");

} finally {

loader.classList.add("hidden");

}

}


function preencherDados(data){

const descricaoClima = data.clima.descricao
? data.clima.descricao.toLowerCase()
: "";

let emoji = "🌤";

if (descricaoClima.includes("chuva")) emoji = "🌧";
else if (descricaoClima.includes("nuvem")) emoji = "☁";
else if (descricaoClima.includes("sol")) emoji = "☀";

document.getElementById("cidade").textContent =
`${emoji} ${data.cidade || "Cidade desconhecida"} - ${data.estado || ""}`;

document.getElementById("rua").textContent =
data.logradouro || "Rua não informada";

document.getElementById("bairro").textContent =
data.bairro || "Bairro não informado";

document.getElementById("temperatura").textContent =
data.clima.temperatura ?? "--";

document.getElementById("descricao").textContent =
data.clima.descricao ?? "--";

document.getElementById("umidade").textContent =
data.clima.umidade ?? "--";

if(data.clima.icone){

document.getElementById("iconeClima").src =
`https://openweathermap.org/img/wn/${data.clima.icone}@2x.png`;

}

document.getElementById("resultado").classList.remove("hidden");

}

async function carregarHistorico() {

const tabela = document.getElementById("historico");
const contador = document.getElementById("contador");

if (!tabela) return;

tabela.innerHTML = "";

try {

const response = await fetch("/api/geoclimate/historico");

if (!response.ok) return;

const data = await response.json();

contador.textContent = data.length;

if (data.length === 0) {

const tr = document.createElement("tr");

tr.innerHTML = `
<td colspan="3">Nenhuma consulta feita ainda</td>
`;

tabela.appendChild(tr);
return;

}

data.forEach(c => {

const tr = document.createElement("tr");

tr.innerHTML = `
<td>${c.cep}</td>
<td>${c.cidade}</td>
<td>${c.estado}</td>
`;

tabela.appendChild(tr);

});

} catch (e) {

console.error("Erro ao carregar histórico", e);

}

}

async function limparHistorico() {

const confirmar = confirm("Deseja realmente apagar o histórico?");

if (!confirmar) return;

try {

await fetch("/api/geoclimate/historico", {
method: "DELETE"
});

carregarHistorico();

} catch (e) {

console.error("Erro ao limpar histórico", e);

}

}

document
.getElementById("cepInput")
.addEventListener("keypress", function(e) {

if (e.key === "Enter") {
buscarCep();
}

});

function aplicarModoAutomatico() {

const hora = new Date().getHours();

if (hora >= 18 || hora <= 6) {
document.body.classList.add("dark");
}

}

function usarLocalizacao() {

    if (!navigator.geolocation) {
        alert("Geolocalização não suportada");
        return;
    }

    navigator.geolocation.getCurrentPosition(async (pos) => {

        const lat = pos.coords.latitude;
        const lon = pos.coords.longitude;

        try {

            const res = await fetch(`/api/geoclimate/coordenadas?lat=${lat}&lon=${lon}`);

            if (!res.ok) {
                throw new Error("Erro na API");
            }

            const data = await res.json();

            preencherDados(data);

        } catch (err) {

            alert("Erro ao buscar clima da localização");
            console.error(err);


        }

    });

}


window.onload = function(){

aplicarModoAutomatico();
carregarHistorico();

};