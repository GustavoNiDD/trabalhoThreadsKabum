import requests
import time
import random
from concurrent.futures import ThreadPoolExecutor, as_completed

def make_request(base_url, mode, cliente_id, produto_id):
    quantidade = random.randint(1, 10)
    payload = {
        "clienteId": cliente_id,
        "detalhes": [
            {
                "produtoId": produto_id,
                "precoVenda": 1999.99,
                "quantidade": quantidade,
                "desconto": 0.00
            }
        ]
    }

    # Adiciona o query param ?mode=<mode>
    url = f"{base_url}?mode={mode}"

    start = time.time()
    try:
        response = requests.post(url, json=payload)
        end = time.time()
        return response.status_code, response.text, (end - start)
    except Exception as e:
        end = time.time()
        return None, str(e), (end - start)

def run_scenario(base_url, mode, n_requests=10, n_threads=5, cliente_id=1, produto_id=1):
    start_global = time.time()

    results = []
    with ThreadPoolExecutor(max_workers=n_threads) as executor:
        futures = [executor.submit(make_request, base_url, mode, cliente_id, produto_id) for _ in range(n_requests)]
        for future in as_completed(futures):
            status, content, req_time = future.result()
            results.append((status, content, req_time))

    end_global = time.time()
    duration = end_global - start_global

    # Tempo médio
    tempos = [r[2] for r in results if r[2] is not None]
    tempo_medio = sum(tempos)/len(tempos) if tempos else 0

    # Status codes
    status_counts = {}
    for r in results:
        sc = r[0]
        if sc not in status_counts:
            status_counts[sc] = 0
        status_counts[sc] += 1

    print("=== Resultados do Cenário ===")
    print(f"Modo: {mode}")
    print(f"Número de requisições: {n_requests}")
    print(f"Tempo médio de resposta: {tempo_medio:.4f} s")
    print(f"Tempo total: {duration:.4f} s")
    print("Status das requisições:")
    for sc, count in status_counts.items():
        print(f"  {sc}: {count} requisições")

def main():
    base_url = "http://localhost:8080/pedidos"

    print("Escolha o tipo de requisição:")
    print("1 - noLock")
    print("2 - Otimista")
    print("3 - Pessimista")
    choice = input("Digite o número da opção: ")

    if choice == "1":
        resp = input("Removeu o atributo de versão? (sim/nao): ").strip().lower()
        if resp == "sim":
            n_requests = int(input("Qual o número de requisições? "))
            run_scenario(base_url, mode="noLock", n_requests=n_requests)
        else:
            print("Atributo de versão não removido. Execução finalizada.")

    elif choice == "2":
        resp = input("Descomentou o campo de versão? (sim/nao): ").strip().lower()
        if resp == "sim":
            n_requests = int(input("Qual o número de requisições? "))
            run_scenario(base_url, mode="optimistic", n_requests=n_requests)
        else:
            print("Campo de versão não descomentado. Execução finalizada.")

    elif choice == "3":
        resp = input("Removeu o atributo de versão? (sim/nao): ").strip().lower()
        if resp == "sim":
            n_requests = int(input("Qual o número de requisições? "))
            run_scenario(base_url, mode="pessimistic", n_requests=n_requests)
        else:
            print("Atributo de versão não removido. Execução finalizada.")
    else:
        print("Opção inválida.")

if __name__ == "__main__":
    main()
