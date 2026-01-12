INSERT INTO exchange_rate (currency_code, rate) VALUES ('USD', 4.0) ON CONFLICT (currency_code) DO NOTHING;
