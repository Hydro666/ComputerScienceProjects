"""
Denero's, strat for finding chance of scoreing at least k points with n rolls
"""

def at_least(k, n):
	if n== 0 and k <= 0:
		return 1
	elif n == 0 and k > 0:
		return 0
	else:
		chance, outcome = 0, 2
		while outcome <= 6:
			chance = chance + 1/6 * at_least(k - outcome, n -1)
			outcome += 1
		return chance

