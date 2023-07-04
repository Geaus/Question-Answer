import {
    Editor,
    editorViewOptionsCtx,
    rootCtx,
    defaultValueCtx,
    editorViewCtx,
    Ctx,
    schemaCtx
} from "@milkdown/core";
import { nord } from "@milkdown/theme-nord";
import { commonmark } from "@milkdown/preset-commonmark";
import { history } from "@milkdown/plugin-history";
import { gfm } from "@milkdown/preset-gfm";
import { ReactEditor, useEditor } from "@milkdown/react";
import {
    tooltip,
    tooltipPlugin,
} from "@milkdown/plugin-tooltip";
import { listener, listenerCtx } from "@milkdown/plugin-listener";
import { prism } from "@milkdown/plugin-prism";
import { block } from "@milkdown/plugin-block";
import { cursor } from "@milkdown/plugin-cursor";
import { clipboard } from "@milkdown/plugin-clipboard";
import { useEffect, useState } from "react";
import { insert } from "@milkdown/utils";

const editable = () => false;

export default function Answer() {
    const [content, setContent] = useState("# hello \nSelect me to annotate me!");

    useEffect(() => {
        console.log("content=", content);
    }, [content]);

    const { editor, getInstance } = useEditor(
        (root) =>
            Editor.make()
                .config((ctx) => {
                    ctx.update(editorViewOptionsCtx, (prev) => ({
                        ...prev,
                        editable,
                    }))
                    ctx.set(rootCtx, root);
                    ctx.set(defaultValueCtx, content);
                    ctx
                        .get(listenerCtx)
                        .beforeMount((ctx) => {
                            console.log("beforeMount");
                        })
                        .mounted((ctx) => {
                            console.log("mounted");
                            insert("# Default Title");
                        })
                        .updated((ctx, doc, prevDoc) => {
                            console.log("updated", doc, prevDoc);
                            console.log("updated JSON", doc.toJSON());
                        })
                        .markdownUpdated((ctx, markdown, prevMarkdown) => {
                            console.log(
                                "markdownUpdated to=",
                                markdown,
                                "\nprev=",
                                prevMarkdown
                            );
                            setContent(markdown);
                        })
                        .blur((ctx) => {
                            console.log("when editor loses focus");
                        })
                        .focus((ctx) => {
                            const view = ctx.get(editorViewCtx);
                            console.log("focus", view);
                        })
                        .destroy((ctx) => {
                            console.log("destroy");
                        });
                })
                .use(tooltip.configure(tooltipPlugin, {
                    top: true,
                }))
                .use(nord)
                .use(commonmark)
                .use(gfm)
                .use(history)
                .use(listener)
                .use(prism)
                .use(block)
                .use(cursor)
                .use(clipboard)
        //.use(slash)
    );

    return (
        <div >
            <h1>Answer</h1>
            <ReactEditor editor={editor} />
            <hr />
        </div>
    );
}
