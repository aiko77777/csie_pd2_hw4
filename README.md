trick:\n
calculate_idf中"每個term在所有docs中出現的次數"在testcase中有很多是重複的，特定一個term對每一筆測資來說都是一樣的值。
e.g.
"at", and, try, pull, "at", them, "at"
12000,299,3456,3333,2222,674,12
當"at"要計算"at在所有docs中出現的次數"時，只需算一次即可。
重複的不列入計算，運算所需時間會大幅降低。
