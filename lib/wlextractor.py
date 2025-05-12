import requests
import fitz
import re
import unicodedata
import sys
import os

def normalizar_texto(texto):
    texto = unicodedata.normalize('NFKD', texto).encode('ASCII', 'ignore').decode('ASCII')
    return texto.lower()

def processa_disciplina(nome_disciplina):
    arquivo_pdf = "oferta_2024-2.pdf"
    if not os.path.exists(arquivo_pdf): 
        url = "https://facom.ufu.br/system/files/conteudo/oferta_disciplinas_bcc_2024-2.pdf" # site da facom - ufu
        res = requests.get(url)
        with open(arquivo_pdf, "wb") as f:
            f.write(res.content)

    doc = fitz.open(arquivo_pdf)
    ch_total = None

    disciplinaNormalizado = normalizar_texto(nome_disciplina)
    palavrasBusca = disciplinaNormalizado.split()

    for pagina in doc:
        texto = pagina.get_text()
        blocos = texto.split("FACOM")
        for bloco in blocos:
            blocoNormalizado = normalizar_texto(bloco)
            todasPalavras = all(palavra in blocoNormalizado for palavra in palavrasBusca)
            if todasPalavras:
                bloco = "FACOM" + bloco
                linhas = bloco.splitlines()
                for i, linha in enumerate(linhas):
                    if "CH Total" in linha:
                        for j in range(i-1, max(i-3, -1), -1):
                            numeros = re.findall(r"\b\d+\b", linhas[j])
                            if numeros:
                                ch_total = numeros[0]
                                break
                        break
                break
        if ch_total:
            break

    return ch_total if ch_total else "-1"

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("-1")
        sys.exit(1)
    disciplina = sys.argv[1].strip()
    resultado = processa_disciplina(disciplina)
    print(resultado)