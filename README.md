# TQ Calculator
Command line tool for streamlining Engineering Economy Calculations

## Setup
1. Download the .jar file on this repository
2. Move the jar file to any location of choice where it will NOT delete (mistakenly)
3. Ensure java is installed on the machine the file is downloaded on
4. Open terminal from the terminal app on Mac or bash command line on windows
5. For Mac : enter `nano .zshrc`
6. For Windows : enter `nano ~/.bashrc` or `nano ~/.bash_profile`
7. Add this line to the file that comes up : `alias tq='java -jar {enter file path to the downloaded .jar file}`
8. Hit `Ctl` + `O` keys to save or equivalent keys
9. Restart your terminal session and `tq` is ready for use!
-------------------------------------------------------------------
## Accepted Commands

### Factors
factor_abbrev = f/p or p/f or a/p or g/p ... etc
```
tq -e "amount(factor_abbrev, interest rate %, periods)"
```
------------------------------------------------------------
### Inflation Calculations
1. Macrs Annual Rate
```
tq -e "1(`macrs_ar`, lifespan, year)"
```
2. Macrs Annual Depreciation Amount
```
tq -e "initial cost(`macrs_ad`, lifespan, year)"
```
3. Macrs Book Value
```
tq -e "initial cost(`macrs_bv`, lifespan, year)"
```
4. Straight Line Annual Rate
```
tq -e "1(`sl_ar`, lifespan, 0)"
```
5. Straight Line Fixed Rate
```
tq -e "1(`sl_ar`, lifespan, 0)"
```
6. Straight Line Annual Depreciation
```
tq -e "initial cost(`sl_ad`, salvage value, lifespan)"
```
7. Straight Line Book Value
```
tq -e "initial cost(`sl_bv`, annual depreciation amount, year)"
```
8. Double Declining Fixed Rate
```
tq -e "1(`ddb_fr`, lifespan, 0)"
```
9. Double Declining Annual Rate
```
tq -e "1(`ddb_ar`, lifespan, year)"
```
10. Double Declining Annual Depreciation
```
tq -e "nitial cost(`ddv_ad`, lifespan, year)"
```
11. Double Declining Book Value
```
tq -e "initial cost(`ddb_bv`, lifespan, year)"
```
12. Declining Balance Annual Depreciation
```
tq -e "initial cost(`db_ad`, stated fixed rate %, year)"
```
13. Declining Balance Book Value
```
tq -e "initial cost(`db_bv`, stated fixed rate %, year)"
```
-----------------------------------

### tq ~ turn-quest Calculator!