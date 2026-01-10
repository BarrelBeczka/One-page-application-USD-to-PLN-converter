const API_URL = '/api';

async function convertCurrency() {
    const amountInput = document.getElementById('amount');
    const directionSelect = document.getElementById('direction');
    const amount = amountInput.value;
    const direction = directionSelect.value;

    if (!amount || amount <= 0) {
        alert('Proszę wprowadzić poprawną kwotę.');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/convert?amount=${amount}&sourceCurrency=${direction}`, {
            method: 'POST'
        });

        if (!response.ok) throw new Error('Błąd serwera');

        const data = await response.json();

        displayResult(data);
        fetchHistory('all'); // Refresh history
    } catch (error) {
        console.error('Error:', error);
        alert('Wystąpił błąd podczas przeliczania.');
    }
}

function displayResult(data) {
    const resultDiv = document.getElementById('result');
    resultDiv.innerHTML = `
        <div>
            <span style="font-size: 0.9em; color: var(--text-secondary);">Otrzymujesz:</span><br>
            <strong style="font-size: 1.5em; color: var(--success-color);">${data.targetAmount.toFixed(2)} ${data.targetCurrency}</strong>
        </div>
        <div style="margin-top: 10px; font-size: 0.8em; color: var(--text-secondary);">
            Kurs: 1 USD = ${data.rateUsed} PLN
        </div>
    `;
    resultDiv.classList.add('visible');
}

async function fetchHistory(filterType) {
    // Update active button state
    document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
    if (filterType === 'all') document.querySelector('button[onclick="filterHistory(\'all\')"]').classList.add('active');
    if (filterType === 'max') document.querySelector('button[onclick="filterHistory(\'max\')"]').classList.add('active');
    if (filterType === 'min') document.querySelector('button[onclick="filterHistory(\'min\')"]').classList.add('active');

    let endpoint = `${API_URL}/history`;
    if (filterType !== 'all') {
        endpoint = `${API_URL}/history/filter?type=${filterType}`;
    }

    try {
        const response = await fetch(endpoint);
        const history = await response.json();
        renderHistory(history);
    } catch (error) {
        console.error('Failed to fetch history:', error);
    }
}

function renderHistory(historyItems) {
    const list = document.getElementById('historyList');
    list.innerHTML = '';

    if (historyItems.length === 0) {
        list.innerHTML = '<div style="text-align:center; color: var(--text-secondary);">Brak historii.</div>';
        return;
    }

    historyItems.forEach(item => {
        const date = new Date(item.timestamp).toLocaleString('pl-PL');
        const el = document.createElement('div');
        el.className = 'history-item';
        el.innerHTML = `
            <div class="history-details">
                <span class="history-values">${item.sourceAmount.toFixed(2)} ${item.sourceCurrency} ➜ ${item.targetAmount.toFixed(2)} ${item.targetCurrency}</span>
                <span class="history-date">${date}</span>
            </div>
            <span class="history-rate">Kurs: ${item.rateUsed}</span>
        `;
        list.appendChild(el);
    });
}

function filterHistory(type) {
    fetchHistory(type);
}

// Initial load
document.addEventListener('DOMContentLoaded', () => {
    fetchHistory('all');
});
