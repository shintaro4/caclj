# caclj

An [elementary cellular automata](https://en.wikipedia.org/wiki/Elementary_cellular_automaton) with clojure.

## Usage

```bash
$ lein run -- --help
```

```bash
An elementary cellular automata.

Usage: lein run [options]

Options:
  -r, --rule RULE    30  Rule number
  -t, --times TIMES  16  The number ob the generations
  -h, --help
```

## Examples

```bash
$ lein run -r 30
```

```bash
                o
               ooo
              oo  o
             oo oooo
            oo  o   o
           oo oooo ooo
          oo  o    o  o
         oo oooo  oooooo
        oo  o   ooo     o
       oo oooo oo  o   ooo
      oo  o    o oooo oo  o
     oo oooo  oo o    o oooo
    oo  o   ooo  oo  oo o   o
   oo oooo oo  ooo ooo  oo ooo
  oo  o    o ooo   o  ooo  o  o
 oo oooo  oo o  o ooooo  ooooooo
```

```bash
$ lein run -r 110 -t 16
```

```bash
                o
               oo
              ooo
             oo o
            ooooo
           oo   o
          ooo  oo
         oo o ooo
        ooooooo o
       oo     ooo
      ooo    oo o
     oo o   ooooo
    ooooo  oo   o
   oo   o ooo  oo
  ooo  oooo o ooo
 oo o oo  ooooo o
```

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
