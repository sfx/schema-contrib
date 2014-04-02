# schema-contrib

Additional validators for [Prismatic's Schema]
(https://github.com/Prismatic/schema).

## Clojars

```clj
[schema-contrib "0.1.0"]
```

## Usage

### Schema

```clj
(ns my-ns
  (:require [schema.core :as s]
            [schema-contrib.core :as sc]))

(s/validate sc/Country "us") ; => "us"

(s/validate sc/Country-Keyword :us) ; => :us

(s/validate sc/Email "brianb@arc90.com") ; => "brianb@arc90.com"

(s/validate sc/Language "en") ; => "en"

(s/validate sc/Language-Keyword :en) ; => :en

(s/validate sc/Path "/dev/null") ; => "/dev/null"

(s/validate sc/URI "https://www.eff.org") ; => "https://www.eff.org"
```

### Generators

You could use our [test.check](https://github.com/clojure/test.check) generators
if you're [generating schema]().

```clj

```

## License

Copyright © 2014 SFX

Distributed under the Eclipse Public License version 1.0.
