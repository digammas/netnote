import js from '@eslint/js'
import globals from 'globals'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import tseslint from 'typescript-eslint'

export default tseslint.config(
    {ignores: ['dist']},
    {
        extends: [js.configs.recommended, ...tseslint.configs.recommended],
        files: ['**/*.{ts,tsx}'],
        languageOptions: {
            ecmaVersion: 2020,
            globals: globals.browser,
        },
        plugins: {
            'react-hooks': reactHooks,
            'react-refresh': reactRefresh,
        },
        rules: {
            ...reactHooks.configs.recommended.rules,
            'react-refresh/only-export-components': [
                'warn',
                {allowConstantExport: true},
            ],
            "@stylistic/ts/object-curly-spacing": ["error"],
            "@stylistic/ts/semi": ["error"],
            "@stylistic/ts/comma-spacing": ["error"],
            "@stylistic/ts/function-call-spacing": ["error"],
            "@stylistic/ts/key-spacing": ["error"],
            "@stylistic/ts/type-annotation-spacing": ["error"],
            "@stylistic/ts/space-infix-ops": ["error"]
        },
    },
)
