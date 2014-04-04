# schema-contrib

Additional validators for [Prismatic's Schema]
(https://github.com/Prismatic/schema).

## Clojars

```clojure
[schema-contrib "0.1.1"]
```

## Usage

### Schema

```clojure
(ns my-ns
  (:require [schema.core :as s]
            [schema-contrib.core :as sc]))

(s/validate sc/Absolute-URI "https://sfx.recruiterbox.com/")

(s/validate sc/Country "us")

(s/validate sc/Country-Keyword :us)

(s/validate sc/Date "2014-01-01")

(s/validate sc/Email "brianb@arc90.com")

(s/validate sc/Language "en")

(s/validate sc/Language-Keyword :en)

(s/validate sc/ISO-Date-Time "2014-04-01T20:17:35+00:00")

(s/validate sc/Path "/dev/null")

(s/validate sc/Time "05:00")

(s/validate sc/URI "https://www.eff.org")
```

### Generators

If you're generating your schema with [test.check]
(https://github.com/clojure/test.check) and [schema-gen]
(https://github.com/MichaelBlume/schema-gen), we have some generators, and are
working on more.

```clojure
(ns my-ns
  (:require [clojure.test.check.generators :as gen]
            [schema-contrib.gen :as scgen]))

(gen/sample scgen/country 5) ; => ("BY" "GF" "MA" "CD" "LB")

(gen/sample scgen/country-keyword 5) ; => (:CI :PT :SZ :VU :BN)

(gen/sample scgen/language 5) ; => ("vo" "br" "oj" "lu" "ss")

(gen/sample scgen/language-keyword 5) ; => (:cr :hu :ak :ki :mk)
```

## License

Copyright © 2014 SFX Entertainment

Distributed under the Eclipse Public License version 1.0.

## Credits

* [Instaparse](https://github.com/Engelberg/instaparse)
* [Schema](https://github.com/prismatic/schema)
* [test.check](https://github.com/clojure/test.check)
