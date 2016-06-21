#!/bin/bash

INSTALL_DIR=`dirname $0`

# Lots of special handling for cygwin, to expand unix-like file references 
#   to their full paths and pass those into the java VM appropriately
if [ $OSTYPE = "cygwin" ]; then

  # Using an array here allows us to manipulate the commandline parameters 
  #   individually, quote them properly, and then pass them on to java. 
  #   Using $@ doesn't allow changing the parameters passed in.
  # Note that it does limit us to a fixed number of parameters

  INSTALL_DIR=`cygpath -w $INSTALL_DIR`

  for (( i = 1 ; i <= $# ; i++ ))
    do
    eval ARGS[$i]=\${$i}
    if [ `expr match "${ARGS[$i]}" '-.*'` == 0 ]; then
        CYGARG=`cygpath -w "${ARGS[$i]}" | tr -d '\n'`
        ARGS[$i]=$CYGARG
    fi
  done

  java -Xmx128m -server -XX:+UseParallelGC -XX:+UseParallelOldGC -jar $INSTALL_DIR/alarm-demo.jar  -O login.properties \
      "${ARGS[1]}" "${ARGS[2]}" "${ARGS[3]}" "${ARGS[4]}" "${ARGS[5]}" "${ARGS[6]}" "${ARGS[7]}" \
      "${ARGS[8]}" "${ARGS[9]}" "${ARGS[10]}" "${ARGS[11]}" "${ARGS[12]}" "${ARGS[13]}" "${ARGS[14]}" \
      "${ARGS[15]}" "${ARGS[16]}" "${ARGS[17]}" "${ARGS[18]}" "${ARGS[19]}" "${ARGS[20]}" "${ARGS[21]}" \
      "${ARGS[22]}" "${ARGS[23]}" "${ARGS[24]}" "${ARGS[25]}" "${ARGS[26]}" "${ARGS[27]}" "${ARGS[28]}" \
      "${ARGS[29]}" "${ARGS[30]}" "${ARGS[31]}" "${ARGS[32]}" "${ARGS[33]}" "${ARGS[34]}" "${ARGS[35]}" \
      "${ARGS[36]}" "${ARGS[37]}" "${ARGS[38]}" "${ARGS[39]}" "${ARGS[40]}" "${ARGS[41]}" "${ARGS[42]}" \
      "${ARGS[43]}" "${ARGS[44]}" "${ARGS[45]}" "${ARGS[46]}" "${ARGS[47]}" "${ARGS[48]}" "${ARGS[49]}"
else
  # Everything's simpler if we're not in Cygwin  
  java -Xmx128m -server -XX:+UseParallelGC -XX:+UseParallelOldGC -jar $INSTALL_DIR/alarm-demo.jar -O login.properties \
      "${1}" "${2}" "${3}" "${4}" "${5}" "${6}" "${7}" "${8}" "${9}" "${10}" \
      "${11}" "${12}" "${13}" "${14}" "${15}" "${16}" "${17}" "${18}" "${19}" "${20}" \
      "${21}" "${22}" "${23}" "${24}" "${25}" "${26}" "${27}" "${28}" "${29}" "${30}" \
      "${31}" "${32}" "${33}" "${34}" "${35}" "${36}" "${37}" "${38}" "${39}" "${40}" \
      "${41}" "${42}" "${43}" "${44}" "${45}" "${46}" "${47}" "${48}" "${49}" "${50}"
fi


