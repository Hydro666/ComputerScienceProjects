"""The Game of Hog."""
#########################################################################################
#########################################################################################
#########################################################################################
#########################################################################################
#########################################################################################


#Henry Lancelle     SID: 20288082
#Ryan Runchey       SID: 22674132


#########################################################################################
#########################################################################################
#########################################################################################
#########################################################################################
#########################################################################################



from dice import four_sided, six_sided, make_test_dice
from ucb import main, trace, log_current_line, interact

GOAL_SCORE = 100 # The goal of Hog is to score 100 points.

######################
# Phase 1: Simulator #
######################

def roll_dice(num_rolls, dice=six_sided):
    """Roll DICE for NUM_ROLLS times. Return either the sum of the outcomes,
    or 1 if a 1 is rolled (Pig out). This calls DICE exactly NUM_ROLLS times.

    num_rolls:  The number of dice rolls that will be made; at least 1.
    dice:       A zero-argument function that returns an integer outcome.
    """
    # These assert statements ensure that num_rolls is a positive integer.
    assert type(num_rolls) == int, 'num_rolls must be an integer.'
    assert num_rolls > 0, 'Must roll at least once.'
    "*** YOUR CODE HERE ***"

    k = 1
    dice_tot = 0
    q = False

    while k <= num_rolls:
        die = dice()
        dice_tot += die
        k += 1
        if  die == 1:
            q = True  

    if q:
        return 1
    else:
        return dice_tot


def take_turn(num_rolls, opponent_score, dice=six_sided):
    """Simulate a turn rolling NUM_ROLLS dice, which may be 0 (Free bacon).

    num_rolls:       The number of dice rolls that will be made.
    opponent_score:  The total score of the opponent.
    dice:            A function of no args that returns an integer outcome.
    """
    assert type(num_rolls) == int, 'num_rolls must be an integer.'
    assert num_rolls >= 0, 'Cannot roll a negative number of dice.'
    assert num_rolls <= 10, 'Cannot roll more than 10 dice.'
    assert opponent_score < 100, 'The game should be over.'
    "*** YOUR CODE HERE ***"
    if num_rolls == 0:
        return max_digit(opponent_score) + 1
    else:
        return roll_dice(num_rolls, dice)

def select_dice(score, opponent_score):
    """Select six-sided dice unless the sum of SCORE and OPPONENT_SCORE is a
    multiple of 7, in which case select four-sided dice (Hog wild).
    """
    "*** YOUR CODE HERE ***"
    if (score + opponent_score) % 7 == 0:
        return four_sided
    else:
        return six_sided

def is_prime(n):
    """Return True if a non-negative number N is prime, otherwise return
    False. 1 is not a prime number!
    """
    assert type(n) == int, 'n must be an integer.'
    assert n >= 0, 'n must be non-negative.'
    k = 2

    if n in [0,1]:
        return False
    if n in [2,3]:
        return True
    while k < n:
        if n % k == 0:
            return False
        k += 1
    return True


def other(who):
    """Return the other player, for a player WHO numbered 0 or 1.

    >>> other(0)
    1
    >>> other(1)
    0
    """
    return 1 - who

def play(strategy0, strategy1, score0=0, score1=0, goal=GOAL_SCORE):
    """Simulate a game and return the final scores of both players, with
    Player 0's score first, and Player 1's score second.

    A strategy is a function that takes two total scores as arguments
    (the current player's score, and the opponent's score), and returns a
    number of dice that the current player will roll this turn.

    strategy0:  The strategy function for Player 0, who plays first
    strategy1:  The strategy function for Player 1, who plays second
    score0   :  The starting score for Player 0
    score1   :  The starting score for Player 1
    """
    who = 0  # Which player is about to take a turn, 0 (first) or 1 (second)
    "*** YOUR CODE HERE ***"


    while score0 < goal and score1 < goal:
        
        if who == 0:
            cur_sco = take_turn(strategy0(score0, score1), score1, select_dice(score0, score1))
            score0 += cur_sco
        else:
            cur_sco = take_turn(strategy1(score1, score0), score0, select_dice(score1, score0))
            score1 += cur_sco
        
        if is_prime(score0 + score1):
            if score0 > score1:
                score0 += cur_sco
            elif score0 < score1:
                score1 += cur_sco
        
        who = other(who)

    return score0, score1  # You may want to change this line.

#######################
# Phase 2: Strategies #
#######################

def always_roll(n):
    """Return a strategy that always rolls N dice.

    A strategy is a function that takes two total scores as arguments
    (the current player's score, and the opponent's score), and returns a
    number of dice that the current player will roll this turn.

    >>> strategy = always_roll(5)
    >>> strategy(0, 0)
    5
    >>> strategy(99, 99)
    5
    """
    def strategy(score, opponent_score):
        return n
    return strategy

# Experiments

def make_averaged(fn, num_samples=10000):
    """Return a function that returns the average_value of FN when called.

    To implement this function, you will have to use *args syntax, a new Python
    feature introduced in this project.  See the project description.

    >>> dice = make_test_dice(3, 1, 5, 6)
    >>> averaged_dice = make_averaged(dice, 1000)
    >>> averaged_dice()
    3.75
    >>> make_averaged(roll_dice, 1000)(2, dice)
    6.0

    In this last example, two different turn scenarios are averaged.
    - In the first, the player rolls a 3 then a 1, receiving a score of 1.
    - In the other, the player rolls a 5 and 6, scoring 11.
    Thus, the average value is 6.0.
    """
    "*** YOUR CODE HERE ***"
    def ave_fun(*args):
        i, total = 1, 0
        while i <= num_samples:
            total += fn(*args)
            i += 1
        return total / num_samples
    return ave_fun

def max_scoring_num_rolls(dice=six_sided):
    """Return the number of dice (1 to 10) that gives the highest average turn
    score by calling roll_dice with the provided DICE.  Assume that dice always
    return positive outcomes.

    >>> dice = make_test_dice(3)
    >>> max_scoring_num_rolls(dice)
    10
    """
    "*** YOUR CODE HERE ***"

    i, cur_max = 1, 0

    while i <= 10:
        cur = make_averaged(roll_dice)(i, dice)
        if cur > cur_max:
            cur_max, cur_die =cur, i
        i += 1
    return cur_die

def winner(strategy0, strategy1):
    """Return 0 if strategy0 wins against strategy1, and 1 otherwise."""
    score0, score1 = play(strategy0, strategy1)
    if score0 > score1:
        return 0
    else:
        return 1

def average_win_rate(strategy, baseline=always_roll(5)):
    """Return the average win rate (0 to 1) of STRATEGY against BASELINE."""
    win_rate_as_player_0 = 1 - make_averaged(winner)(strategy, baseline)
    win_rate_as_player_1 = make_averaged(winner)(baseline, strategy)
    return (win_rate_as_player_0 + win_rate_as_player_1) / 2 # Average results

####################################################################
####################################################################
####################################################################
####################################################################

                        #User made

def max_digit(num):
    return int(max(repr(num)))


def seven_strat(score, opponent_score):
    
    """Retruns True if bacon strategy would yield a multiple of 7
    >>> seven_strat(18, 25)
    True
    >>> seven_strat(4,5)
    False
    >>> seven_strat(0,3)
    True
    """

    return (max_digit(opponent_score) + 1 + score + opponent_score) % 7 == 0



def score_difference(score, opponent_score):
    return score - opponent_score

std = [1.683, 3.623, 5.680, 7.7633, 9.3942, 10.977, 12.177, 13.174, 14.392, 14.17]
expt = [3.5, 5.8611, 7.366, 8.234, 8.636, 8.7027, 8.535, 8.210, 7.783, 7.730]
prob_of_1 = [0.1667, 0.3056, 0.4213, 0.5178, 0.5981, 0.6651, 0.7209, 0.7674, 0.8062, 0.8385]

def spread(dice_number):
    return std[dice_number - 1] + expt[dice_number - 1]

def dice_from_spread(score):
    """return dice number closest to 100 that is in the spread
    >>> dice_from_spread(87)
    3
    >>> dice_from_spread(81)
    6
    """

    i = 1
    l = 100
    r = 0
    while i <= 10:
        if abs(spread(i) - score) < l:
            l = abs(spread(i) - (100 - score))
            r = i
            i += 1
    return a

####################################################################
####################################################################
####################################################################
####################################################################

def run_experiments():
    """Run a series of strategy experiments and report results."""
    if False: # Change to False when done finding max_scoring_num_rolls
        six_sided_max = max_scoring_num_rolls(six_sided)
        print('Max scoring num rolls for six-sided dice:', six_sided_max)
        four_sided_max = max_scoring_num_rolls(four_sided)
        print('Max scoring num rolls for four-sided dice:', four_sided_max)

    if False: # Change to True to test always_roll(8)
        print('always_roll(8) win rate:', average_win_rate(always_roll(8)))

    if False: # Change to True to test bacon_strategy
        print('bacon_strategy win rate:', average_win_rate(bacon_strategy))

    if False: # Change to True to test prime_strategy
        print('prime_strategy win rate:', average_win_rate(prime_strategy))

    if True: # Change to True to test final_strategy
        print('final_strategy win rate:', average_win_rate(final_strategy))

    "*** You may add additional experiments as you wish ***"

# Strategies

def bacon_strategy(score, opponent_score, margin=8, num_rolls=5):
    """This strategy rolls 0 dice if that gives at least MARGIN points,
    and rolls NUM_ROLLS otherwise.
    """
    "*** YOUR CODE HERE ***"
    if max_digit(opponent_score) + 1 >= margin:
        return 0
    else:
        return num_rolls

def prime_strategy(score, opponent_score, margin=8, num_rolls=5):
    """This strategy rolls 0 dice when it results in a beneficial boost and
    rolls NUM_ROLLS if rolling 0 dice gives the opponent a boost. It also
    rolls 0 dice if that gives at least MARGIN points and rolls NUM_ROLLS
    otherwise.
    """
    "*** YOUR CODE HERE ***"
    score_bacon = max_digit(opponent_score) + 1 + score
    if is_prime(score_bacon + opponent_score):
        if score_bacon > opponent_score:
            return 0
        else:
            return num_rolls
    return bacon_strategy(score, opponent_score, margin, num_rolls) # Replace this statement

def final_strategy(score, opponent_score):
    """Write a brief description of your final strategy.

    *** YOUR DESCRIPTION HERE ***
    """
    "*** YOUR CODE HERE ***"
    s, os = score, opponent_score 
    sco_dif = score_difference(s, os)
    s_turn, os_turn = score_difference(100, s) // 8.5, score_difference(100, os) // 7.5
    # These are the approximate turns left if we assume that each player on average gets 8.5 points.
    # Opponents expectation value of only rolling 5 dice each time is 7.3657.  
    from_goal = 100 - s
    if from_goal <= 19 and from_goal % 2 == 1:
            return prime_strategy(s, os, (from_goal - 1) / 2, 3) # Will choose bacon_strategy if doing so would land directly on 100 or better
    elif (s + os) % 7 == 0:
        return 4                                                 # 4 has highest expectation value for 4-sided dice
    elif sco_dif > 0:
        if sco_dif > 10 and seven_strat(s, os):                  # Will do bacon_strategy if doing so would result in a sum of scores that is a multiple of 7.                  
            return 0
        else:
            return prime_strategy(s, os, 8, 3)                   # Decided to play is safe and only roll 3 dice while in the lead to avoid pig out.
    else:
        return 6


#Old code: this was a very competitive process, and unfortunately, we cannot move forward with your codicy for final_strategy. 
    """if sco_dif > 0:
        if sco_dif > 30:
            if seven_strat(s, os):
                return 0
            else:
                return bacon_strategy(s, os, 7, 7)
        elif sco_dif > 15:
            if seven_strat(s, os):
                return 0
            else:
                return bacon_strategy(s, os, 8, 7)
        else:
            return prime_strategy(s, os, 8, 7)
    else:
        if sco_dif < 30:
            return 8
        elif sco_dif < 20:
            if seven_strat(s, os):
                return 0
            else:
                return prime_strategy(s, os, 9, 8)
        else:
            return 9
    """


##########################
# Command Line Interface #
##########################

# Note: Functions in this section do not need to be changed.  They use features
#       of Python not yet covered in the course.


@main
def run(*args):
    """Read in the command-line argument and calls corresponding functions.

    This function uses Python syntax/techniques not yet covered in this course.
    """
    import argparse
    parser = argparse.ArgumentParser(description="Play Hog")
    parser.add_argument('--run_experiments', '-r', action='store_true',
                        help='Runs strategy experiments')
    args = parser.parse_args()

    if args.run_experiments:
        run_experiments()

##############################################################################
# User made from here down




from statistics import stdev, mean, median, mode


def mk_list(func, samples = 1000):
    """Make an array of values of a function after it is called 
    a certian amount of times to perform statistical manipulations
    and analysis on."""
 
    def mk_fun(*args):
        i, l = 1, []
        while i <= samples:
            l.append(func(*args))
            i += 1
        return l
    return mk_fun


def roll(num_rolls, dice=six_sided):
    """Roll DICE for NUM_ROLLS times. Return either the sum of the outcomes,
    or 1 if a 1 is rolled (Pig out). This calls DICE exactly NUM_ROLLS times.

    num_rolls:  The number of dice rolls that will be made; at least 1.
    dice:       A zero-argument function that returns an integer outcome.
    """
    # These assert statements ensure that num_rolls is a positive integer.
    assert type(num_rolls) == int, 'num_rolls must be an integer.'
    assert num_rolls > 0, 'Must roll at least once.'
    "*** YOUR CODE HERE ***"

    k = 1
    dice_tot = 0

    while k <= num_rolls:
        die = dice()
        dice_tot += die
        k += 1  

    else:
        return dice_tot